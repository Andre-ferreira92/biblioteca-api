package com.bibliotecaandre.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestUpdateBookDTO(

        @NotBlank(message = "O título do livro é obrigatório")
        String title,

        @NotBlank(message = "O autor do livro é obrigatório")
        String author,

        @NotNull(message = "A categoria do livro é obrigatória")
        Long categoryId
)
{ }
