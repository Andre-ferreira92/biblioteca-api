package com.bibliotecaandre.biblioteca.stories.book.insertbook;

import com.bibliotecaandre.biblioteca.dto.RequestBookDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseBookDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class InsertBookControllerTest {

    @InjectMocks
    private InsertBookController insertBookController;
    @Mock
    private InsertBookService insertBookService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(insertBookController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldInsertBook() throws Exception {

        RequestBookDTO requestBookDTO = new RequestBookDTO("teste","nome","hp123",1L);
        ResponseBookDTO responseBookDTO = new ResponseBookDTO(1L, "teste", "nome", "hp123","terror");

        when(insertBookService.createBook(any(RequestBookDTO.class))).thenReturn(responseBookDTO);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("teste"))
                .andExpect(jsonPath("$.author").value("nome"))
                .andExpect(jsonPath("$.isbn").value("hp123"))
                .andExpect(jsonPath("$.categoryId").value("terror"));

        verify(insertBookService, times(1)).createBook(any(RequestBookDTO.class));
        verifyNoMoreInteractions(insertBookService);
    }

    @Test
    void shouldReturnNameIsEmpty() throws Exception {
        RequestBookDTO requestBookDTO = new RequestBookDTO("", "nome", "hp123", 1L);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBookDTO)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(insertBookService);
    }
}
