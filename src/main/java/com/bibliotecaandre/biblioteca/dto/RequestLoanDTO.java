package com.bibliotecaandre.biblioteca.dto;

public record RequestLoanDTO(
        Long bookCopyId,
        Long userId
) { }
