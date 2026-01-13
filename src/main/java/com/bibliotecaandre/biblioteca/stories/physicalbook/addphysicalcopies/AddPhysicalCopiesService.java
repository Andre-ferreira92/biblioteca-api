package com.bibliotecaandre.biblioteca.stories.physicalbook.addphysicalcopies;

import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.BookCopy;
import com.bibliotecaandre.biblioteca.model.BookCopyStatus;
import com.bibliotecaandre.biblioteca.repository.BookCopyRepository;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AddPhysicalCopiesService {

    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void addBookCopies(Long id, int quantity) {

        Book book = bookRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        for (int i = 0; i < quantity; i++) {
            BookCopy newCopy = new BookCopy();

            newCopy.setBook(book);
            newCopy.setStatus(BookCopyStatus.AVAILABLE);
            newCopy.setInventoryCode(book.getIsbn() + "-" + UUID.randomUUID().toString().substring(0, 8));
            bookCopyRepository.save(newCopy);
        }
    }
}
