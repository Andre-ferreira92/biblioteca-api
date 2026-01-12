package com.bibliotecaandre.biblioteca.stories.book.updatebook;

import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UpdateBookService {

    private final BookRepository bookRepository;

        @Transactional
        public Book updateBook(Long id, Book bookDetails) {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setIsbn(bookDetails.getIsbn());
            return book;
    }
}
