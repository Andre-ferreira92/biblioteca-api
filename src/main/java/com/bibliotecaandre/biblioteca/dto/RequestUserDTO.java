package com.bibliotecaandre.biblioteca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestUserDTO(
        @NotBlank(message = "User name is required")
        String name,

        @Email(message = "Invalid email format")
        @NotBlank(message = "User email is required")
        String email,

        @Size(min = 8, message = "Password must be at least 8 characters long")
        @NotBlank(message = "Password is required")
        String password
)
{ }
