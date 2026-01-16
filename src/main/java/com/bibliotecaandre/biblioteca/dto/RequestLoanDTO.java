package com.bibliotecaandre.biblioteca.dto;

import jakarta.validation.constraints.NotNull;

public record RequestLoanDTO(

        @NotNull(message = "O id do user é obrigatório")
        Long userId,

        @NotNull(message = "O id do PhysicalBook é obrigatório")
        Long physicalBookId
) { }
