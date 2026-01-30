package com.bibliotecaandre.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestBookDTO(
        @NotBlank(message = "Book title is required")
        String title,

        @NotBlank(message = "Book author is required")
        String author,

        @NotBlank(message = "ISBN field is required")
        @Size(min = 2, max = 10, message = "ISBN must be between 2 and 10 characters")
        String isbn,

        @NotNull(message = "Book category is required")
        Long categoryId
) { }
