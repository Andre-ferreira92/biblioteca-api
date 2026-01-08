package com.bibliotecaandre.biblioteca.repository;
import com.bibliotecaandre.biblioteca.model.BookCopy;
import com.bibliotecaandre.biblioteca.model.BookCopyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    List<BookCopy> findByStatus(BookCopyStatus status);
    boolean existsByBookIdAndStatus(Long bookId, BookCopyStatus status);
    List<BookCopy> findByBookId(Long id);
}
