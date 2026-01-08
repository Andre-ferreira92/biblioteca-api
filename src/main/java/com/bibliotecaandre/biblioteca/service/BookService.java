package com.bibliotecaandre.biblioteca.service;

import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.repository.BookCopyRepository;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;

    //lista de todos os livros
    public List<Book> findAllBooks() {
        return bookRepository.findAll();

    }
    //update de um livro que esteja disponivel
    public Book updatebook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id" + id));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        return bookRepository.save(book);
    }

    public Book addBook(Book bookDetails) {
        return bookRepository.save(bookDetails);

    }
}
