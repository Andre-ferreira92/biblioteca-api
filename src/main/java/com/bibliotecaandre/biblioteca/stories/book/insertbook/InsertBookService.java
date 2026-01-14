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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class InsertBookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ResponseBookDTO createBook(RequestBookDTO dto) {
        // 1. Validação de Regra de Negócio
        if (bookRepository.existsByIsbn(dto.isbn())) {
            throw new IsbnAlreadyExistsException();
        }

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(ResourceNotFoundException::new);

        Book book = new Book();
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setIsbn(dto.isbn());
        book.setCategory(category);
        // 2. Persistência (GUARDAR PRIMEIRO)
        // O save retorna o objeto "book" já com o ID gerado pela BD
        Book savedBook = bookRepository.save(book);

        // 3. Transformação para DTO e Retorno
        return new ResponseBookDTO(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getAuthor(),
                savedBook.getIsbn(),
                savedBook.getCategory().getName()
        );
    }
}
