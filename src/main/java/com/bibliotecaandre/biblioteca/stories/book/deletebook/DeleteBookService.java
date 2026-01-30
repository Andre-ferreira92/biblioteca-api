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
        Book book = bookRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        // Se houver cópias LOANED,nao pode apagar o título
        boolean hasActiveLoans = physicalBookRepository.existsByBookIdAndStatus(id, PhysicalBookStatus.LOANED);
        if (hasActiveLoans) {
            log.warn("This book has active loans and cannot be deleted.");
            throw new BusinessRuleException("Cannot delete the book because there are copies currently on loan.");
        }
        // Primeiro apagamos as cópias vinculadas a este livro
        List<PhysicalBook> copies = physicalBookRepository.findByBookId(id);
        physicalBookRepository.deleteAll(copies);
        log.warn("Removed copies for book with ID {} ", id);

        //apagamos o título do catálogo
        bookRepository.delete(book);
        log.info("Book with ID {} successfully removed ", id);
    }
}
