package com.bibliotecaandre.biblioteca.stories.book.getbook;

import com.bibliotecaandre.biblioteca.dto.ResponseBookDTO;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllBooksService {

    private final BookRepository bookRepository;

    public List<ResponseBookDTO> findAllBooks() {
        List<Book> book = bookRepository.findAll();

        return book.stream()
                .map(books -> new ResponseBookDTO(
                        books.getId(),
                        books.getTitle(),
                        books.getAuthor(),
                        books.getIsbn(),
                        books.getCategory() != null ? books.getCategory().getName() : "Sem Categoria"
                ))
                .toList();
    }
}

