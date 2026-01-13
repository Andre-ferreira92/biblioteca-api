package com.bibliotecaandre.biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCopy bookCopy = (BookCopy) o; // <--- Aqui dizes que o "o" é um Book
        return id != null && id.equals(bookCopy.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
