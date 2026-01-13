package com.bibliotecaandre.biblioteca.stories.book.insertbook;

import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class InsertBookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book createBook(Book bookDetails) {
        if (bookRepository.existsByIsbn(bookDetails.getIsbn())) {
            throw new RuntimeException("ISBN já cadastrado!");
        }
        return bookRepository.save(bookDetails);

    }
}
