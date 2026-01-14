package com.bibliotecaandre.biblioteca.dto;

public record RequestUpdateBookDTO(
        String title,
        String author,
        Long categoryId
)
{ }
