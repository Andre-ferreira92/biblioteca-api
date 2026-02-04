package com.bibliotecaandre.biblioteca.stories.physicalbook.addphysicalcopies;

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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AddPhysicalCopiesService {

    private final PhysicalBookRepository physicalBookRepository;
    private final BookRepository bookRepository;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void addBookCopies(Long id, int quantity) {
        log.info("Starting process to add copies to book catalog");

        Book book = findBookById(id);
        List<PhysicalBook> newCopies = createPhysicalCopies(book, quantity);
        saveAllCopies(newCopies);

        log.info("Added {} copies of book with ID: {}", quantity, book.getId());
    }

    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private List<PhysicalBook> createPhysicalCopies(Book book, int quantity) {
        List<PhysicalBook> copies = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            copies.add(createSingleCopy(book));
        }
        return copies;
    }

    private PhysicalBook createSingleCopy(Book book) {
        PhysicalBook copy = new PhysicalBook();
        copy.setBook(book);
        copy.setStatus(PhysicalBookStatus.AVAILABLE);
        copy.setInventoryCode(generateInventoryCode(book.getIsbn()));
        return copy;
    }

    private String generateInventoryCode(String isbn) {
        return isbn + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private void saveAllCopies(List<PhysicalBook> copies) {
        physicalBookRepository.saveAll(copies);
    }
}
