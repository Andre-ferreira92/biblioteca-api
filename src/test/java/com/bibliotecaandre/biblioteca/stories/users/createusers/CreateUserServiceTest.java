package com.bibliotecaandre.biblioteca.stories.users.createusers;

import com.bibliotecaandre.biblioteca.dto.RequestUserDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseUserDTO;
import com.bibliotecaandre.biblioteca.model.Roles;
import com.bibliotecaandre.biblioteca.model.User;
import com.bibliotecaandre.biblioteca.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    CreateUserService createUserService;

    @Test
    void createUserSuccess() {

        RequestUserDTO requestUserDTO = new RequestUserDTO("Test User", "test@gmail.com", "12345678");
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Test User");
        savedUser.setEmail("test@gmail.com");
        savedUser.setPassword("$2a$12$f8wRli5QOcVuAH6PHKUCzub9es.5y/1wBptM9hspzla3ZHWpzpPtq");
        savedUser.setRole(Roles.USER);
        savedUser.setCreatedAt(LocalDateTime.now());

        when(passwordEncoder.encode("12345678")).thenReturn("$2a$12$f8wRli5QOcVuAH6PHKUCzub9es.5y/1wBptM9hspzla3ZHWpzpPtq");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        ResponseUserDTO responseUserDTO = createUserService.createUser(requestUserDTO);

        assertNotNull(responseUserDTO);
        assertEquals(responseUserDTO.name(),savedUser.getName());
        assertEquals(responseUserDTO.email(),savedUser.getEmail());
        assertEquals(responseUserDTO.role(),savedUser.getRole());

        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode("12345678");
        verifyNoMoreInteractions(passwordEncoder,userRepository);
    }
}