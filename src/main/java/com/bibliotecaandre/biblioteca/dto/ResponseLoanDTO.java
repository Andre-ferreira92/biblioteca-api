package com.bibliotecaandre.biblioteca.dto;

public record ResponseLoanDTO(
        String bookTitle,
        Long bookCopyId,
        java.time.LocalDate dueDate
) {}
