package com.bibliotecaandre.biblioteca.service;


import com.bibliotecaandre.biblioteca.model.BookCopy;
import com.bibliotecaandre.biblioteca.model.BookCopyStatus;
import com.bibliotecaandre.biblioteca.repository.BookCopyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;

    //lista de todos os livros disponiveis
    public List<BookCopy> findAllAvailableBookCopies() {
        return bookCopyRepository.findByStatus(BookCopyStatus.AVAILABLE);
    }

    public BookCopy save(BookCopy bookCopy) {
        bookCopy.setStatus(BookCopyStatus.AVAILABLE);
        return bookCopyRepository.save(bookCopy);
    }



//    //Entrada de um novo livro
//    public Book save(Book book BookCopy bookcopy) {
//        //coloca o status como available por defeito
//        book.setStatus(BookCopyStatus.AVAILABLE);
//        return bookRepository.save(book);
//    }
//
//    //update de um livro que esteja disponivel
//    public Book updatebook(Long id, Book bookDetails) {
//        Book book = bookRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Book not found with id" + id));
//        if(book.getStatus() == BookCopyStatus.LOANED) {
//            throw new RuntimeException("cannot update loaned book");
//        }
//        book.setTitle(bookDetails.getTitle());
//        book.setAuthor(bookDetails.getAuthor());
//        return bookRepository.save(book);
//    }
//    //delete de um livro que esteja disponivel
//    public void deletebook(Long id) {
//        Book book = bookRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Book not found with id" + id));
//        if(book.getStatus() == BookCopyStatus.LOANED) {
//            throw new RuntimeException("cannot delete loaned book");
//        }
//        bookRepository.delete(book);
//    }
}