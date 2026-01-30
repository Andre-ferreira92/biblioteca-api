package com.bibliotecaandre.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestUpdateBookDTO(

        @NotBlank(message = "Book title is required")
        String title,

        @NotBlank(message = "Book author is required")
        String author,

        @NotNull(message = "Book category is required")
        Long categoryId
)
{ }
