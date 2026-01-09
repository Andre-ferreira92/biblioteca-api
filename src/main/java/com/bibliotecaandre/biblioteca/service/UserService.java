package com.bibliotecaandre.biblioteca.service;

import com.bibliotecaandre.biblioteca.dto.RequestUserDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseUserDTO;
import com.bibliotecaandre.biblioteca.model.Roles;
import com.bibliotecaandre.biblioteca.model.User;
import com.bibliotecaandre.biblioteca.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    public ResponseUserDTO saveUser(RequestUserDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        user.setRole(Roles.USER);
        User savedUser = userRepository.save(user);
         return new ResponseUserDTO(
                 savedUser.getName(),
                 savedUser.getEmail()
         );
    }
}
