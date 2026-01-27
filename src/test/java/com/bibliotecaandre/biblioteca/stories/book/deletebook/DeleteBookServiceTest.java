package com.bibliotecaandre.biblioteca.stories.book.deletebook;

import com.bibliotecaandre.biblioteca.exceptions.BusinessRuleException;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.PhysicalBook;
import com.bibliotecaandre.biblioteca.model.PhysicalBookStatus;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import com.bibliotecaandre.biblioteca.repository.PhysicalBookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteBookServiceTest {

    @InjectMocks
    DeleteBookService deleteBookService;
    @Mock
    BookRepository bookRepository;
    @Mock
    PhysicalBookRepository physicalBookRepository;

    @Test
    void deleteBookByIdSuccess() {
       Book book = new Book();
       book.setId(1L);
       book.setTitle("Book Title");

        PhysicalBook physicalBook = new PhysicalBook();
        physicalBook.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(physicalBookRepository.existsByBookIdAndStatus(1L, PhysicalBookStatus.LOANED)).thenReturn(false);
        when(physicalBookRepository.findByBookId(1L)).thenReturn(Arrays.asList(physicalBook));
        doNothing().when(physicalBookRepository).deleteAll(Arrays.asList(physicalBook));
        doNothing().when(bookRepository).delete(book);

        deleteBookService.deleteBook(1L);

        verify(bookRepository, times(1)).findById(1L);
        verify(physicalBookRepository, times(1)).existsByBookIdAndStatus(1L, PhysicalBookStatus.LOANED);
        verify(physicalBookRepository, times(1)).findByBookId(1L);
        verify(physicalBookRepository, times(1)).deleteAll(Arrays.asList(physicalBook));
        verify(bookRepository, times(1)).delete(book);
    }
    @Test
    void deleteBookNotFound(){
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,() -> deleteBookService.deleteBook(1L));
        verify(bookRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(bookRepository,physicalBookRepository);
    }

    @Test
    void deleteBookWhenHasActiveLoansThrowsException() {
        Book book = new Book();
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(physicalBookRepository.existsByBookIdAndStatus(1L, PhysicalBookStatus.LOANED)).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> deleteBookService.deleteBook(1L));

        verify(bookRepository, times(1)).findById(1L);
        verify(physicalBookRepository, times(1)).existsByBookIdAndStatus(1L, PhysicalBookStatus.LOANED);
        // Não deve chamar deleteAll nem delete
        verifyNoMoreInteractions(physicalBookRepository, bookRepository);
    }

    @Test
    void deleteBookWithNoPhysicalCopies() {
        Book book = new Book();
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(physicalBookRepository.existsByBookIdAndStatus(1L, PhysicalBookStatus.LOANED)).thenReturn(false);
        when(physicalBookRepository.findByBookId(1L)).thenReturn(Arrays.asList()); // Lista vazia

        deleteBookService.deleteBook(1L);

        verify(bookRepository, times(1)).findById(1L);
        verify(physicalBookRepository, times(1)).existsByBookIdAndStatus(1L, PhysicalBookStatus.LOANED);
        verify(physicalBookRepository, times(1)).findByBookId(1L);
        verify(physicalBookRepository, times(1)).deleteAll(Arrays.asList()); // Lista vazia
        verify(bookRepository, times(1)).delete(book);
    }



}