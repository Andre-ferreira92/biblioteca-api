package com.bibliotecaandre.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestBookDTO(
        @NotBlank(message = "O título do livro é obrigatório")
        String title,

        @NotBlank(message = "O autor do livro é obrigatório")
        String author,

        @NotBlank(message = "O campo ISBN é obrigatório")
        @Size(min = 2, max = 10, message = "O ISBN deve conter entre 2 e 10 caracteres")
        String isbn,

        @NotNull(message = "A categoria do livro é obrigatória")
        Long categoryId
) { }
