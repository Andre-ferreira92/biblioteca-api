package com.bibliotecaandre.biblioteca.service;

import com.bibliotecaandre.biblioteca.dto.ResponseBookCopyDTO;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.BookCopy;
import com.bibliotecaandre.biblioteca.model.BookCopyStatus;
import com.bibliotecaandre.biblioteca.repository.BookCopyRepository;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;

    //lista de todos os livros disponiveis
    public List<ResponseBookCopyDTO> findAllAvailableBookCopies() {
        // 1. Procurar as cópias no banco de dados
        List<BookCopy> copies = bookCopyRepository.findByStatus(BookCopyStatus.AVAILABLE);
        // 2. Transformar a lista
        return copies.stream()
                .map(copy -> new ResponseBookCopyDTO(
                        copy.getId(),
                        copy.getInventoryCode(),
                        copy.getStatus(),
                        copy.getBook().getTitle()
                ))
                .toList();
    }

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