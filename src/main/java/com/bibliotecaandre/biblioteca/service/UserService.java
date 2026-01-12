package com.bibliotecaandre.biblioteca.service;

import com.bibliotecaandre.biblioteca.dto.RequestUserDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseUserDTO;
import com.bibliotecaandre.biblioteca.model.Roles;
import com.bibliotecaandre.biblioteca.model.User;
import com.bibliotecaandre.biblioteca.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    @Transactional  //aqui devo usar ? tenho um save mas nao o posso tirar porque ele é que tem o id ou neste caso nao porque o objeto user nao esta a guardar o id ?
    public ResponseUserDTO saveUser(RequestUserDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
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
