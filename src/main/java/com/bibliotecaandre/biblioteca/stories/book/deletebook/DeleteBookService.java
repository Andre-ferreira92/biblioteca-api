package com.bibliotecaandre.biblioteca.stories.book.deletebook;

import com.bibliotecaandre.biblioteca.exceptions.BusinessRuleException;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.BookCopy;
import com.bibliotecaandre.biblioteca.model.BookCopyStatus;
import com.bibliotecaandre.biblioteca.repository.BookCopyRepository;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DeleteBookService {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        // Se houver cópias LOANED,nao pode apagar o título
        boolean hasActiveLoans = bookCopyRepository.existsByBookIdAndStatus(id, BookCopyStatus.LOANED);
        if (hasActiveLoans) {
            throw new BusinessRuleException("Não é possível apagar o livro porque existem cópias emprestadas.");
        }
        // Primeiro apagamos as cópias vinculadas a este livro
        List<BookCopy> copies = bookCopyRepository.findByBookId(id);
        bookCopyRepository.deleteAll(copies);

        //apagamos o título do catálogo
        bookRepository.delete(book);
    }
}
