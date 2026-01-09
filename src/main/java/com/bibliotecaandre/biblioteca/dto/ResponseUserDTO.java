package com.bibliotecaandre.biblioteca.dto;

import com.bibliotecaandre.biblioteca.model.Roles;

import java.time.LocalDateTime;

public record ResponseUserDTO(
        Long id,
        String name,
        String email,
        Roles role,
        LocalDateTime createdAt
)
{ }