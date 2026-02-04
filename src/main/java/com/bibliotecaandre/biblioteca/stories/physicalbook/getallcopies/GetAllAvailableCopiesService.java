package com.bibliotecaandre.biblioteca.stories.physicalbook.getallcopies;

import com.bibliotecaandre.biblioteca.dto.ResponsePhysicalBookDTO;
import com.bibliotecaandre.biblioteca.model.PhysicalBook;
import com.bibliotecaandre.biblioteca.model.PhysicalBookStatus;
import com.bibliotecaandre.biblioteca.repository.PhysicalBookRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllAvailableCopiesService {

    private final PhysicalBookRepository physicalBookRepository;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ResponsePhysicalBookDTO> findAllAvailableBookCopies() {
        List<PhysicalBook> copies = physicalBookRepository.findByStatus(PhysicalBookStatus.AVAILABLE);
        return copies.stream()
                .map(copy -> new ResponsePhysicalBookDTO(
                        copy.getId(),
                        copy.getInventoryCode(),
                        copy.getStatus(),
                        copy.getBook().getTitle()
                ))
                .toList();
    }
}