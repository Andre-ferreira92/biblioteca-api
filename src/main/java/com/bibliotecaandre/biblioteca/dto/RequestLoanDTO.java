package com.bibliotecaandre.biblioteca.dto;

import jakarta.validation.constraints.NotNull;

public record RequestLoanDTO(

        @NotNull(message = "User ID is required")
        Long userId,

        @NotNull(message = "Physical Book ID is required")
        Long physicalBookId
) { }
