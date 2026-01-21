package com.bibliotecaandre.biblioteca.stories.book.deletebook;

import com.bibliotecaandre.biblioteca.exceptions.BusinessRuleException;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.PhysicalBook;
import com.bibliotecaandre.biblioteca.model.PhysicalBookStatus;
import com.bibliotecaandre.biblioteca.repository.PhysicalBookRepository;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DeleteBookService {

    private final BookRepository bookRepository;
    private final PhysicalBookRepository physicalBookRepository;

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        // Se houver cópias LOANED,nao pode apagar o título
        boolean hasActiveLoans = physicalBookRepository.existsByBookIdAndStatus(id, PhysicalBookStatus.LOANED);
        if (hasActiveLoans) {
            log.warn("Este livro tem copias emprestadas não pode apagar");
            throw new BusinessRuleException("Não é possível apagar o livro porque existem cópias emprestadas.");
        }
        // Primeiro apagamos as cópias vinculadas a este livro
        List<PhysicalBook> copies = physicalBookRepository.findByBookId(id);
        physicalBookRepository.deleteAll(copies);
        log.warn("Removidas copias de livro com id {} ", id);

        //apagamos o título do catálogo
        bookRepository.delete(book);
        log.info("livro com id {} removido com sucesso ", id);
    }
}
