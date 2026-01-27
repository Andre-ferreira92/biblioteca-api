package com.bibliotecaandre.biblioteca.stories.book.insertbook;

import com.bibliotecaandre.biblioteca.dto.RequestBookDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseBookDTO;
import com.bibliotecaandre.biblioteca.exceptions.IsbnAlreadyExistsException;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.Category;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import com.bibliotecaandre.biblioteca.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InsertBookServiceTest {

    @InjectMocks
    InsertBookService insertBookService;
    @Mock
    BookRepository bookRepository;
    @Mock
    CategoryRepository categoryRepository;

    @Test
    void createBookSuccess() {

        RequestBookDTO requestBookDTO = new RequestBookDTO("livro","nome","livro123",1L);

        Category category = new Category();
        category.setId(1L);
        category.setName("terror");


        Book book = new Book();
        book.setId(1L);
        book.setTitle("livro");
        book.setAuthor("nome");
        book.setIsbn("livro123");
        book.setCategory(category);

        when(bookRepository.existsByIsbn("livro123")).thenReturn(false);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(bookRepository.save(any())).thenReturn(book);

        ResponseBookDTO responseBookDTO = insertBookService.createBook(requestBookDTO);

        assertEquals(1L, responseBookDTO.id());
        assertEquals("livro", responseBookDTO.title());
        assertEquals("nome", responseBookDTO.author());
        assertEquals("livro123", responseBookDTO.isbn());
        assertEquals("terror", responseBookDTO.categoryId());

        verify(bookRepository, times(1)).existsByIsbn("livro123");
        verify(categoryRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void isbnAlreadyExistsException(){

        RequestBookDTO requestBookDTO = new RequestBookDTO("livro","nome","livro123",1L);

        when(bookRepository.existsByIsbn("livro123")).thenReturn(true);

        assertThrows(IsbnAlreadyExistsException.class, () -> insertBookService.createBook(requestBookDTO));

        verify(bookRepository, times(1)).existsByIsbn("livro123");
        // Não deve chamar deleteAll nem delete
        verifyNoMoreInteractions(bookRepository,categoryRepository);
    }

    @Test
    void resourceNotFoundException() {

        RequestBookDTO requestBookDTO = new RequestBookDTO("livro","nome","livro123",1L);

        when(bookRepository.existsByIsbn("livro123")).thenReturn(false);
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class , () -> insertBookService.createBook(requestBookDTO));

        verify(categoryRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).existsByIsbn("livro123");
        verifyNoMoreInteractions(categoryRepository,bookRepository);
    }
}