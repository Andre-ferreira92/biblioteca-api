package com.bibliotecaandre.biblioteca.stories.users.createusers;

import com.bibliotecaandre.biblioteca.dto.RequestUserDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseUserDTO;
import com.bibliotecaandre.biblioteca.model.Roles;
import com.bibliotecaandre.biblioteca.model.User;
import com.bibliotecaandre.biblioteca.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseUserDTO createUser(RequestUserDTO dto) {
        User user = createUserEntity(dto);
        User savedUser = userRepository.save(user);
        return buildUserResponseDTO(savedUser);
    }

    private User createUserEntity(RequestUserDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(encodePassword(dto.password()));
        user.setRole(Roles.USER);
        return user;
    }

    private String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    private ResponseUserDTO buildUserResponseDTO(User user) {
        return new ResponseUserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
