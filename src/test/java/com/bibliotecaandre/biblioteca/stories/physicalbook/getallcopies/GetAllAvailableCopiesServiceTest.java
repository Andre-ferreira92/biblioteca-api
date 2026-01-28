package com.bibliotecaandre.biblioteca.stories.physicalbook.getallcopies;

import com.bibliotecaandre.biblioteca.dto.ResponsePhysicalBookDTO;
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

import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllAvailableCopiesServiceTest {

    @Mock
    PhysicalBookRepository physicalBookRepository;
    @InjectMocks
    GetAllAvailableCopiesService getAllAvailableCopiesService;

    @Test
    void findAllAvailableBookCopiesSuccess() {

        Book book1 = new Book();
        book1.setTitle("Livro 1");

        Book book2 = new Book();
        book2.setTitle("Livro 2");

        PhysicalBook physicalBook1 = new PhysicalBook();
        physicalBook1.setId(1L);
        physicalBook1.setInventoryCode("INV-001");
        physicalBook1.setStatus(PhysicalBookStatus.AVAILABLE);
        physicalBook1.setBook(book1);

        PhysicalBook physicalBook2 = new PhysicalBook();
        physicalBook2.setId(2L);
        physicalBook2.setInventoryCode("INV-002");
        physicalBook2.setStatus(PhysicalBookStatus.AVAILABLE);
        physicalBook2.setBook(book2);

        when(physicalBookRepository.findByStatus(PhysicalBookStatus.AVAILABLE))
                .thenReturn(List.of(physicalBook1, physicalBook2));

        List<ResponsePhysicalBookDTO> physicalBooks = getAllAvailableCopiesService.findAllAvailableBookCopies();

        assertEquals(2, physicalBooks.size());
        assertEquals(1L, physicalBooks.get(0).id());
        assertEquals("INV-001", physicalBooks.get(0).inventoryCode());
        assertEquals(PhysicalBookStatus.AVAILABLE, physicalBooks.get(0).status());
        assertEquals("Livro 1", physicalBooks.get(0).bookTitle());

        assertEquals(2L, physicalBooks.get(1).id());
        assertEquals("INV-002", physicalBooks.get(1).inventoryCode());
        assertEquals(PhysicalBookStatus.AVAILABLE, physicalBooks.get(1).status());
        assertEquals("Livro 2", physicalBooks.get(1).bookTitle());

        verify(physicalBookRepository, times(1)).findByStatus(PhysicalBookStatus.AVAILABLE);
        verifyNoMoreInteractions(physicalBookRepository);
    }

    @Test
    void getAllAvailableBookCopiesWhenReturnIsEmpty(){

        when(physicalBookRepository.findByStatus(PhysicalBookStatus.AVAILABLE)).thenReturn(List.of());

        List<ResponsePhysicalBookDTO> physicalBooks = getAllAvailableCopiesService.findAllAvailableBookCopies();

        assertEquals(0, physicalBooks.size());

        verify(physicalBookRepository, times(1)).findByStatus(PhysicalBookStatus.AVAILABLE);
        verifyNoMoreInteractions(physicalBookRepository);
    }
}