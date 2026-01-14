package com.bibliotecaandre.biblioteca.stories.book.updatebook;

import com.bibliotecaandre.biblioteca.dto.RequestBookDTO;
import com.bibliotecaandre.biblioteca.dto.RequestUpdateBookDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseBookDTO;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.Category;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import com.bibliotecaandre.biblioteca.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@AllArgsConstructor
public class UpdateBookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ResponseBookDTO updateBook(Long id, RequestUpdateBookDTO dto) {
        //Fazer alterações para gravar na DB
        Book book = bookRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(ResourceNotFoundException::new);

        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setCategory(category);

        //Gravar na DB
        Book updatedBook = bookRepository.save(book);

        //Passar para a resposta dto
        return new ResponseBookDTO(
                updatedBook.getId(),
                updatedBook.getTitle(),
                updatedBook.getAuthor(),
                updatedBook.getIsbn(),
                updatedBook.getCategory().getName()
        );
    }
}