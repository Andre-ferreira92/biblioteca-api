package com.bibliotecaandre.biblioteca.stories.book.searchbookwithfilters;

import com.bibliotecaandre.biblioteca.dto.ResponseBookDTO;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchWithFilterServiceTest {

    @Mock
    BookRepository bookRepository;
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    SearchWithFilterService searchWithFilterService;

    @Test
    void findAllBooksSuccess() {

        Category category = new Category();
        category.setId(1L);
        category.setName("test");

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("book 1");
        book1.setAuthor("author 1");
        book1.setIsbn("ISBN 1");
        book1.setCategory(category);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("book 2");
        book2.setAuthor("author 2");
        book2.setIsbn("ISBN 2");
        book2.setCategory(category);

        List<Book> books = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);

        List<ResponseBookDTO> responseBookDTOS = searchWithFilterService.findAllBooks(null);

        assertEquals(2, responseBookDTOS.size());
        assertEquals("book 1", responseBookDTOS.get(0).title());
        assertEquals("author 1", responseBookDTOS.get(0).author());
        assertEquals("ISBN 1", responseBookDTOS.get(0).isbn());
        assertEquals("test", responseBookDTOS.get(0).categoryId());
        assertEquals("book 2", responseBookDTOS.get(1).title());
        assertEquals("author 2", responseBookDTOS.get(1).author());
        assertEquals("ISBN 2", responseBookDTOS.get(1).isbn());
        assertEquals("test", responseBookDTOS.get(1).categoryId());


        verify(bookRepository, times(1)).findAll();
        verifyNoMoreInteractions(bookRepository,categoryRepository);
    }

    @Test
    void findAllBooksWithFilterSuccess() {

        Category category = new Category();
        category.setId(1L);
        category.setName("test");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("book 1");
        book.setAuthor("author 1");
        book.setIsbn("ISBN 1");
        book.setCategory(category);

        List<Book> books = List.of(book);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(bookRepository.findByCategoryId(1L)).thenReturn(books);

        List<ResponseBookDTO> responseBookDTOS = searchWithFilterService.findAllBooks(1L);

        assertEquals(1, responseBookDTOS.size());
        assertEquals("book 1", responseBookDTOS.get(0).title());
        assertEquals("author 1", responseBookDTOS.get(0).author());
        assertEquals("ISBN 1", responseBookDTOS.get(0).isbn());
        assertEquals("test", responseBookDTOS.get(0).categoryId());

        verify(categoryRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).findByCategoryId(1L);
        verifyNoMoreInteractions(bookRepository, categoryRepository);
    }

    @Test
    void findBookByIdSuccess() {

        Category category = new Category();
        category.setId(1L);
        category.setName("test");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("book 1");
        book.setAuthor("author 1");
        book.setIsbn("ISBN 1");
        book.setCategory(category);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        ResponseBookDTO responseBookDTO = searchWithFilterService.findBookById(1L);

        assertEquals(1L, responseBookDTO.id());
        assertEquals("book 1", responseBookDTO.title());
        assertEquals("author 1", responseBookDTO.author());
        assertEquals("ISBN 1", responseBookDTO.isbn());
        assertEquals("test", responseBookDTO.categoryId());

        verify(bookRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void findBookByIdWhenNotFoundThrowsException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> searchWithFilterService.findBookById(1L));

        verify(bookRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void findAllBooksWithFilterWhenCategoryNotFoundThrowsException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> searchWithFilterService.findAllBooks(1L));

        verify(categoryRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(categoryRepository, bookRepository);
    }
}