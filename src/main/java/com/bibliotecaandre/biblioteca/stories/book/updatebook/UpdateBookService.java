package com.bibliotecaandre.biblioteca.stories.book.updatebook;

import com.bibliotecaandre.biblioteca.dto.RequestUpdateBookDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseBookDTO;
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
public class UpdateBookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseBookDTO updateBook(Long id, RequestUpdateBookDTO dto) {
        log.info("Updating book with ID {}", id);

        Book book = findBookById(id);
        Category category = findCategoryById(dto.categoryId());
        updateBookData(book, dto, category);
        Book updatedBook = saveBook(book);

        log.info("Book update with ID {} successfully saved", id);
        return buildBookResponseDTO(updatedBook);
    }

    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private void updateBookData(Book book, RequestUpdateBookDTO dto, Category category) {
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setCategory(category);
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