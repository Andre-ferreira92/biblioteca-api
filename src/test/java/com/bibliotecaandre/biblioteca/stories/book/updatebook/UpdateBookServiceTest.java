package com.bibliotecaandre.biblioteca.stories.book.updatebook;


import com.bibliotecaandre.biblioteca.dto.RequestUpdateBookDTO;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class UpdateBookServiceTest {

    @InjectMocks
    UpdateBookService updateBookService;
    @Mock
    BookRepository bookRepository;
    @Mock
    CategoryRepository categoryRepository;

    @Test
    void updateBookSuccess() {

        RequestUpdateBookDTO requestDTO = new RequestUpdateBookDTO("Novo Título", "Novo Autor", 1L);

        Category category = new Category();
        category.setId(1L);
        category.setName("Ficção");

        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Título Antigo");
        existingBook.setAuthor("Autor Antigo");
        existingBook.setIsbn("ISBN123");
        existingBook.setCategory(category);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        ResponseBookDTO response = updateBookService.updateBook(1L, requestDTO);

        assertEquals(1L, response.id());
        assertEquals("Novo Título", response.title());
        assertEquals("Novo Autor", response.author());
        assertEquals("ISBN123", response.isbn());
        assertEquals("Ficção", response.categoryId());

        verify(bookRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(existingBook);
        verifyNoMoreInteractions(bookRepository, categoryRepository);
    }

    @Test
    void updateBookWhenBookNotFoundThrowsException() {

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> updateBookService.updateBook(1L, new RequestUpdateBookDTO("Novo Título", "Novo Autor", 1L)));

        verify(bookRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(bookRepository, categoryRepository);
    }

    @Test
    void updateBookWhenCategoryNotFoundThrowsException() {

        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Título Antigo");
        existingBook.setAuthor("Autor Antigo");
        existingBook.setIsbn("ISBN123");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> updateBookService.updateBook(1L, new RequestUpdateBookDTO("Novo Título", "Novo Autor", 1L)));

        verify(bookRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(categoryRepository,bookRepository);
    }

}