package com.bibliotecaandre.biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "book_copies")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book book;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookCopyStatus status;

    @Column(nullable = false, unique = true)
    private String inventoryCode;

    @Column(nullable = false, unique = false)
    private String isbn;

}
