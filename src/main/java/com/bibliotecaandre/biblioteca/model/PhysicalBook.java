package com.bibliotecaandre.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book_copies")
public class PhysicalBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book book;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PhysicalBookStatus status;

    @Column(nullable = false, unique = true)
    private String inventoryCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysicalBook physicalBook = (PhysicalBook) o;
        return id != null && id.equals(physicalBook.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
