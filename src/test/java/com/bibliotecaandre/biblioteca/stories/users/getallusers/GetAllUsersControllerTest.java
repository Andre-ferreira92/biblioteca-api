package com.bibliotecaandre.biblioteca.stories.users.getallusers;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GetAllUsersControllerTest {

    @Mock
    private GetAllUsersService getAllUsersService;

    @InjectMocks
    private GetAllUsersController getAllUsersController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(getAllUsersController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        List<ResponseUserDTO> users = Arrays.asList(
                new ResponseUserDTO(1L, "andre1", "a1@gmail.com", Roles.USER, LocalDateTime.now()),
                new ResponseUserDTO(2L, "andre2", "a2@gmail.com", Roles.USER, LocalDateTime.now())
        );

        when(getAllUsersService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("andre1"))
                .andExpect(jsonPath("$[0].email").value("a1@gmail.com"))
                .andExpect(jsonPath("$[0].role").value("USER"))
                .andExpect(jsonPath("$[1].name").value("andre2"))
                .andExpect(jsonPath("$[1].email").value("a2@gmail.com"))
                .andExpect(jsonPath("$[1].role").value("USER"));

        verify(getAllUsersService, times(1)).findAllUsers();
        verifyNoMoreInteractions(getAllUsersService);
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldReturnEmptyList() throws Exception {

        when(getAllUsersService.findAllUsers()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(getAllUsersService, times(1)).findAllUsers();
        verifyNoMoreInteractions(getAllUsersService);
    }
}