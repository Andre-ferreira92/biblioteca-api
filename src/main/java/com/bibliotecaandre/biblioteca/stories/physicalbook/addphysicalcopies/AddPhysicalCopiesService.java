package com.bibliotecaandre.biblioteca.stories.physicalbook.addphysicalcopies;

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

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AddPhysicalCopiesService {

    private final PhysicalBookRepository physicalBookRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void addBookCopies(Long id, int quantity) {

        log.info("Inicio de processo de adicionar copias a livro do catalogo");
        Book book = bookRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        for (int i = 0; i < quantity; i++) {
            PhysicalBook newCopy = new PhysicalBook();

            newCopy.setBook(book);
            newCopy.setStatus(PhysicalBookStatus.AVAILABLE);
            newCopy.setInventoryCode(book.getIsbn() + "-" + UUID.randomUUID().toString().substring(0, 8));
            physicalBookRepository.save(newCopy);
        }
        log.info("Foram adicionadas: {} copias do livro com id: {}", quantity, book.getId());
    }
}
