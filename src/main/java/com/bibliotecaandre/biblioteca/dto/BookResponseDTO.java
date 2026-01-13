package com.bibliotecaandre.biblioteca.dto;

public record BookResponseDTO(
        Long id,
        String title,
        String author,
        String isbn,
        String categoryName
)
{ }
