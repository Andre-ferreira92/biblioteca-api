package com.bibliotecaandre.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestCategoryDTO(
        @NotBlank(message = "Category name is required")
        String name
)
{ }
