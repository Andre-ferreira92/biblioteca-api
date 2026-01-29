package com.bibliotecaandre.biblioteca.stories.users.getallusers;

import com.bibliotecaandre.biblioteca.dto.ResponseUserDTO;
import com.bibliotecaandre.biblioteca.model.Roles;
import com.bibliotecaandre.biblioteca.model.User;
import com.bibliotecaandre.biblioteca.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllUsersServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    GetAllUsersService getAllUsersService;

    @Test
    void findAllUsersSuccess() {

        User user1 = new User();
        user1.setId(1L);
        user1.setName("andre1");
        user1.setEmail("a1@gmail.com");
        user1.setRole(Roles.USER);
        user1.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setId(2L);
        user2.setName("andre2");
        user2.setEmail("a2@gmail.com");
        user2.setRole(Roles.USER);
        user2.setCreatedAt(LocalDateTime.now());

        List<User> users = Arrays.asList(user1,user2);

        when(userRepository.findAll()).thenReturn(users);

        List<ResponseUserDTO> result = getAllUsersService.findAllUsers();

        assertEquals(2, result.size());
        assertEquals("andre1", result.get(0).name());
        assertEquals("a1@gmail.com", result.get(0).email());
        assertEquals("andre2", result.get(1).name());
        assertEquals("a2@gmail.com", result.get(1).email());

        verify(userRepository, times(1)).findAll();

    }
    @Test
    void findAllUsersEmptyList() {

        when(userRepository.findAll()).thenReturn(Arrays.asList());

        List<ResponseUserDTO> result = getAllUsersService.findAllUsers();

        assertEquals(0, result.size());
        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }
}