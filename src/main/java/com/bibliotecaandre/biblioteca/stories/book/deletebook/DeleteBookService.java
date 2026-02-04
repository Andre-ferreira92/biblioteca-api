package com.bibliotecaandre.biblioteca.stories.book.deletebook;

import com.bibliotecaandre.biblioteca.exceptions.BusinessRuleException;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.PhysicalBook;
import com.bibliotecaandre.biblioteca.model.PhysicalBookStatus;
import com.bibliotecaandre.biblioteca.repository.PhysicalBookRepository;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DeleteBookService {

    private final BookRepository bookRepository;
    private final PhysicalBookRepository physicalBookRepository;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBook(Long id) {
        Book book = findBookById(id);
        validateNoActiveLoans(id);
        deletePhysicalCopies(id);
        deleteBookFromCatalog(book);

        log.info("Book with ID {} successfully removed", id);
    }

    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private void validateNoActiveLoans(Long bookId) {
        boolean hasActiveLoans = physicalBookRepository.existsByBookIdAndStatus(bookId, PhysicalBookStatus.LOANED);
        if (hasActiveLoans) {
            log.warn("This book has active loans and cannot be deleted.");
            throw new BusinessRuleException("Cannot delete the book because there are copies currently on loan.");
        }
    }

    private void deletePhysicalCopies(Long bookId) {
        List<PhysicalBook> copies = physicalBookRepository.findByBookId(bookId);
        physicalBookRepository.deleteAll(copies);
        log.warn("Removed copies for book with ID {}", bookId);
    }

    private void deleteBookFromCatalog(Book book) {
        bookRepository.delete(book);
    }
}
