package com.bibliotecaandre.biblioteca.repository;
import com.bibliotecaandre.biblioteca.model.PhysicalBook;
import com.bibliotecaandre.biblioteca.model.PhysicalBookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhysicalBookRepository extends JpaRepository<PhysicalBook, Long> {
    List<PhysicalBook> findByStatus(PhysicalBookStatus status);
    boolean existsByBookIdAndStatus(Long bookId, PhysicalBookStatus status);
    List<PhysicalBook> findByBookId(Long id);
}
