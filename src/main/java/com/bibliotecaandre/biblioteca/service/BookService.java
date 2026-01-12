package com.bibliotecaandre.biblioteca.service;

import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.BookCopy;
import com.bibliotecaandre.biblioteca.model.BookCopyStatus;
import com.bibliotecaandre.biblioteca.repository.BookCopyRepository;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import jakarta.transaction.Transactional;
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
    //update de um livro existente
    @Transactional
    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        return book;
    }

    //Insere novo livro
    public Book addBook(Book bookDetails) {
        return bookRepository.save(bookDetails);

    }

    @Transactional //
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        // Se houver cópias LOANED,nao pode apagar o título
        boolean hasActiveLoans = bookCopyRepository.existsByBookIdAndStatus(id, BookCopyStatus.LOANED);
        if (hasActiveLoans) {
            throw new RuntimeException("Cannot delete book: There are copies currently loaned to users.");
        }
        // Primeiro apagamos as cópias vinculadas a este livro
        List<BookCopy> copies = bookCopyRepository.findByBookId(id);
        bookCopyRepository.deleteAll(copies);

        //apagamos o título do catálogo
        bookRepository.delete(book);
    }
}
