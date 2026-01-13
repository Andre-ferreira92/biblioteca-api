package com.bibliotecaandre.biblioteca.dto;

import java.time.LocalDateTime;

public record ResponseLoanDTO(
        Long id,
        String userName,
        String bookTitle,
        LocalDateTime dueDate,
        LocalDateTime returnDate,
        String Status
)
{}
