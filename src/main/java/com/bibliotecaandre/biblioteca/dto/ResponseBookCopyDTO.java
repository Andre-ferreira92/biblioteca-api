package com.bibliotecaandre.biblioteca.dto;

import com.bibliotecaandre.biblioteca.model.BookCopyStatus;

public record ResponseBookCopyDTO(
        Long id,
        String inventoryCode,
        BookCopyStatus status,
        String bookTitle)
{ }
