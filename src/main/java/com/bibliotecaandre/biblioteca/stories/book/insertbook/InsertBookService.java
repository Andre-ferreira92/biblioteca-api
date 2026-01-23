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
        log.info("A criar um novo livro no catelogo da biblioteca");
        if (bookRepository.existsByIsbn(dto.isbn())) {
            log.warn("Este isbn ja existe {}", dto.isbn());
            throw new IsbnAlreadyExistsException();
        }

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(ResourceNotFoundException::new);

        Book book = new Book();
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setIsbn(dto.isbn());
        book.setCategory(category);

        Book savedBook = bookRepository.save(book);
        log.info("Novo livro criado com sucesso: ID {} - Título: {}", savedBook.getId(), savedBook.getTitle());
        return new ResponseBookDTO(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getAuthor(),
                savedBook.getIsbn(),
                savedBook.getCategory().getName()
        );
    }
}
