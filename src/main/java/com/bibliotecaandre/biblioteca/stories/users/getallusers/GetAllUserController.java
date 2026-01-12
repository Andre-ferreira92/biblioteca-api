package com.bibliotecaandre.biblioteca.stories.users.getallusers;

import com.bibliotecaandre.biblioteca.dto.ResponseUserDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Tag(name = "Users", description = "Gestão dos Users")
public class GetAllUserController {

    private final GetAllUsersService getAllUsersService;

    @GetMapping
    public ResponseEntity<List<ResponseUserDTO>> getAllUsers() {
        List<ResponseUserDTO> users = getAllUsersService.findAllUsers();
        return ResponseEntity.ok(users);
    }
}
