package com.bibliotecaandre.biblioteca.stories.physicalbook.addphysicalcopies;

import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.PhysicalBook;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import com.bibliotecaandre.biblioteca.repository.PhysicalBookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddPhysicalCopiesServiceTest {

    @Mock
    PhysicalBookRepository physicalBookRepository;
    @Mock
    BookRepository bookRepository;
    @InjectMocks
    AddPhysicalCopiesService addPhysicalCopiesService;

    @Test
    void addBookCopiesSuccess() {
        Book book = new Book();
        book.setId(1L);
        book.setIsbn("ISBN123");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(physicalBookRepository.saveAll(anyList())).thenAnswer(invocation -> {
            List<PhysicalBook> saved = invocation.getArgument(0);
            saved.forEach(copy -> copy.setId(1L));
            return saved;
        });

        addPhysicalCopiesService.addBookCopies(1L, 2);

        verify(bookRepository, times(1)).findById(1L);
        verify(physicalBookRepository, times(1)).saveAll(anyList());  // ✅ Corrigido
        verifyNoMoreInteractions(bookRepository, physicalBookRepository);
    }

    @Test
    void addBookCopiesResourceNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addPhysicalCopiesService.addBookCopies(1L, 1));

        verify(bookRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(bookRepository, physicalBookRepository);
    }
}