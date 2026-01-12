package com.bibliotecaandre.biblioteca.service;

import com.bibliotecaandre.biblioteca.dto.ResponseBookCopyDTO;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.BookCopy;
import com.bibliotecaandre.biblioteca.model.BookCopyStatus;
import com.bibliotecaandre.biblioteca.repository.BookCopyRepository;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;


    public List<ResponseBookCopyDTO> findAllAvailableBookCopies() {
        List<BookCopy> copies = bookCopyRepository.findByStatus(BookCopyStatus.AVAILABLE);
        return copies.stream()
                .map(copy -> new ResponseBookCopyDTO(
                        copy.getId(),
                        copy.getInventoryCode(),
                        copy.getStatus(),
                        copy.getBook().getTitle()
                ))
                .toList();
    }

    //faz varias coisas aqui ve se o livro existe pelo id e depois adiciona quantidade de copias se fizer transactional aqui garanto que so sao adicionadas todas as copias ao mesmo tempo . ou neste caso preciso de fazer save uma a uma por estar dentro de um for .
    @Transactional
    public void addBookCopies(Long id, int quantity) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        for(int i = 0; i < quantity; i++) {
            BookCopy newCopy = new BookCopy();

            newCopy.setBook(book);
            newCopy.setStatus(BookCopyStatus.AVAILABLE);
            newCopy.setInventoryCode(book.getIsbn() + "-" + UUID.randomUUID().toString().substring(0,8));
            bookCopyRepository.save(newCopy);
        }
    }
}