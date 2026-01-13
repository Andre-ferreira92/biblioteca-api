package com.bibliotecaandre.biblioteca.dto;

public record BookRequestDTO(
        String title,
        String author,
        String isbn,
        Long categoryId
)
{ }
