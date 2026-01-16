package com.bibliotecaandre.biblioteca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestUserDTO(
        @NotBlank(message = "O nome do utilizador é obrigatório")
        String name,

        @Email(message = "O formato do e-mail é inválido")
        @NotBlank(message = "O email do utilizador é obrigatório")
        String email,

        @Size(min = 8, message = "A password deve ter pelo menos 8 caracteres")
        @NotBlank(message = "A password é obrigatório")
        String password
)
{ }
