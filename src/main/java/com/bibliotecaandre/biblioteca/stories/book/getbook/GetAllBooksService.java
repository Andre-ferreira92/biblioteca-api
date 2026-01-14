package com.bibliotecaandre.biblioteca.stories.book.getbook;

import com.bibliotecaandre.biblioteca.dto.ResponseBookDTO;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.Category;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import com.bibliotecaandre.biblioteca.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllBooksService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public List<ResponseBookDTO> findAllBooks(Long categoryId) {

        List<Book> books;

        if (categoryId == null) {

            books = bookRepository.findAll();
        }
        else{

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(ResourceNotFoundException::new);

            books = bookRepository.findByCategoryId(categoryId);

        }
        return books.stream()
                .map(book -> new ResponseBookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getIsbn(),
                        book.getCategory() != null ? book.getCategory().getName() : "Sem Categoria"
                ))
                .toList();
    }

    public ResponseBookDTO findBookById(Long id) {
        Book book = bookRepository.findById(id)
                    .orElseThrow(ResourceNotFoundException::new);

        return new ResponseBookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getCategory() != null ? book.getCategory().getName() : "Sem Categoria"
        );
    }
}

