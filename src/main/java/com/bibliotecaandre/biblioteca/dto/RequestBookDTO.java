package com.bibliotecaandre.biblioteca.dto;

public record RequestBookDTO(
        String title,
        String author,
        String isbn,
        Long categoryId
)
{ }
