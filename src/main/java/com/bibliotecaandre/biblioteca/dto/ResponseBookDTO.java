package com.bibliotecaandre.biblioteca.dto;

public record ResponseBookDTO(
        Long id,
        String title,
        String author,
        String isbn,
        String categoryName
)
{ }
