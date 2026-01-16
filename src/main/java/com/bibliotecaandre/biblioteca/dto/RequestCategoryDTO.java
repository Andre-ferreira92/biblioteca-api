package com.bibliotecaandre.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestCategoryDTO(
        @NotBlank(message = "O nome da categoria é obrigatório")
        String name
)
{ }
