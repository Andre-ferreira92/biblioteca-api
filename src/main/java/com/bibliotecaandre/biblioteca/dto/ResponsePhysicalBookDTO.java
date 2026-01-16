package com.bibliotecaandre.biblioteca.dto;

import com.bibliotecaandre.biblioteca.model.PhysicalBookStatus;

public record ResponsePhysicalBookDTO(
        Long id,
        String inventoryCode,
        PhysicalBookStatus status,
        String bookTitle
) { }
