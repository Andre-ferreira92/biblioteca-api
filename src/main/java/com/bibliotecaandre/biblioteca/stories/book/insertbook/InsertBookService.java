package com.bibliotecaandre.biblioteca.stories.book.insertbook;

import com.bibliotecaandre.biblioteca.dto.RequestBookDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseBookDTO;
import com.bibliotecaandre.biblioteca.exceptions.IsbnAlreadyExistsException;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.Category;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import com.bibliotecaandre.biblioteca.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class InsertBookService {


    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseBookDTO createBook(RequestBookDTO dto) {
        log.info("Creating a new book in the library catalog");

        validateIsbnUniqueness(dto.isbn());
        Category category = findCategoryById(dto.categoryId());
        Book newBook = createBookEntity(dto, category);
        Book savedBook = saveBook(newBook);

        log.info("New book created successfully: ID {} - Title: {}", savedBook.getId(), savedBook.getTitle());
        return buildBookResponseDTO(savedBook);
    }

    private void validateIsbnUniqueness(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            log.warn("This ISBN already exists: {}", isbn);
            throw new IsbnAlreadyExistsException();
        }
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private Book createBookEntity(RequestBookDTO dto, Category category) {
        Book book = new Book();
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setIsbn(dto.isbn());
        book.setCategory(category);
        return book;
    }

    private Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    private ResponseBookDTO buildBookResponseDTO(Book book) {
        return new ResponseBookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getCategory().getName()
        );
    }
}
