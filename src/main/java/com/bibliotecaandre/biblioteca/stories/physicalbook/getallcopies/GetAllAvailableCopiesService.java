package com.bibliotecaandre.biblioteca.stories.physicalbook.getallcopies;

import com.bibliotecaandre.biblioteca.dto.ResponseBookCopyDTO;
import com.bibliotecaandre.biblioteca.model.BookCopy;
import com.bibliotecaandre.biblioteca.model.BookCopyStatus;
import com.bibliotecaandre.biblioteca.repository.BookCopyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllAvailableCopiesService {

    private final BookCopyRepository bookCopyRepository;

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
}