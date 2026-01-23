package com.bibliotecaandre.biblioteca.stories.users.getallusers;

import com.bibliotecaandre.biblioteca.dto.ResponseUserDTO;
import com.bibliotecaandre.biblioteca.model.User;
import com.bibliotecaandre.biblioteca.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllUsersService {

    private UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public List<ResponseUserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new ResponseUserDTO(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getRole(),
                        user.getCreatedAt()
                ))
                .toList();
    }
}
