package com.bibliotecaandre.biblioteca.stories.book.searchbookwithfilters;

import com.bibliotecaandre.biblioteca.dto.ResponseBookDTO;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.Category;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import com.bibliotecaandre.biblioteca.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SearchWithFilterService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public List<ResponseBookDTO> findAllBooks(Long categoryId) {
        List<Book> books = searchBooksByCategory(categoryId);
        return books.stream()
                .map(this::buildBookResponseDTO)
                .toList();
    }

    public ResponseBookDTO findBookById(Long id) {
        Book book = findBookEntityById(id);
        return buildBookResponseDTO(book);
    }

    private List<Book> searchBooksByCategory(Long categoryId) {
        if (categoryId == null) {
            return bookRepository.findAll();
        } else {
            validateCategoryExists(categoryId);
            return bookRepository.findByCategoryId(categoryId);
        }
    }

    private void validateCategoryExists(Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private Book findBookEntityById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private ResponseBookDTO buildBookResponseDTO(Book book) {
        return new ResponseBookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                getCategoryName(book)
        );
    }

    private String getCategoryName(Book book) {
        return book.getCategory() != null ?
                book.getCategory().getName() :
                "Category not defined";
    }
}

