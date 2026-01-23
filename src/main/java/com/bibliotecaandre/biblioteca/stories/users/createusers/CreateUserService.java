package com.bibliotecaandre.biblioteca.stories.users.createusers;

import com.bibliotecaandre.biblioteca.dto.RequestUserDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseUserDTO;
import com.bibliotecaandre.biblioteca.model.Roles;
import com.bibliotecaandre.biblioteca.model.User;
import com.bibliotecaandre.biblioteca.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        String encodedPassword = passwordEncoder.encode(dto.password());
        user.setPassword(encodedPassword);
        user.setRole(Roles.USER);

        User savedUser = userRepository.save(user);

        return new ResponseUserDTO(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getRole(),
                savedUser.getCreatedAt()
        );
    }
}
