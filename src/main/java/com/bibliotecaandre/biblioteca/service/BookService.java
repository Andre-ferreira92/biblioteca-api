package com.bibliotecaandre.biblioteca.service;

import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.BookStatus;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();

    }
    public List<Book> findAllAvailableBooks() {
        return bookRepository.findByStatus(BookStatus.AVAILABLE);
    }

    public Book save(Book book) {
        book.setStatus(BookStatus.AVAILABLE);
        return bookRepository.save(book);
    }

    public Book updatebook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id" + id));
        if(book.getStatus() == BookStatus.LOANED) {
            throw new RuntimeException("cannot update loaned book");
        }
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        return bookRepository.save(book);
    }

    public void deletebook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id" + id));
        if(book.getStatus() == BookStatus.LOANED) {
            throw new RuntimeException("cannot delete loaned book");
        }
        bookRepository.delete(book);
    }
}
