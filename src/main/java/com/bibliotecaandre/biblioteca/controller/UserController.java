package com.bibliotecaandre.biblioteca.controller;

import com.bibliotecaandre.biblioteca.dto.RequestUserDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseUserDTO;
import com.bibliotecaandre.biblioteca.model.User;
import com.bibliotecaandre.biblioteca.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseUserDTO> createUser(@RequestBody RequestUserDTO dto){
        ResponseUserDTO user = userService.saveUser(dto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
