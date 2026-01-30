package com.bibliotecaandre.biblioteca.stories.users.createusers;

import com.bibliotecaandre.biblioteca.dto.RequestUserDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseUserDTO;
import com.bibliotecaandre.biblioteca.model.Roles;
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

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CreateUserControllerTest {

    @InjectMocks
    private CreateUserController createUserController;
    @Mock
    private CreateUserService createUserService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(createUserController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldCreateUser() throws Exception {

        RequestUserDTO requestUserDTO = new RequestUserDTO("teste","teste@teste.com","12345678");
        ResponseUserDTO responseUserDTO = new ResponseUserDTO(1L,"teste","teste@teste.com", Roles.USER, LocalDateTime.now());

        when(createUserService.createUser(any(RequestUserDTO.class))).thenReturn(responseUserDTO);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("teste"))
                .andExpect(jsonPath("$.email").value("teste@teste.com"))
                .andExpect(jsonPath("$.role").value("USER"));

        verify(createUserService, times(1)).createUser(any(RequestUserDTO.class));  // Adicione "times(1)"
        verifyNoMoreInteractions(createUserService);
    }

    @Test
    void shouldReturnEmailInvalid() throws  Exception {

        RequestUserDTO requestUserDTO = new RequestUserDTO("teste", "teste-teste","12345678");

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestUserDTO)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(createUserService);
    }
}
