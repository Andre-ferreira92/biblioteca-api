package com.bibliotecaandre.biblioteca.service;

import com.bibliotecaandre.biblioteca.dto.RequestLoanDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseLoanDTO;
import com.bibliotecaandre.biblioteca.model.BookCopy;
import com.bibliotecaandre.biblioteca.model.BookCopyStatus;
import com.bibliotecaandre.biblioteca.model.Loan;
import com.bibliotecaandre.biblioteca.model.User;
import com.bibliotecaandre.biblioteca.repository.BookCopyRepository;
import com.bibliotecaandre.biblioteca.repository.LoanRepository;
import com.bibliotecaandre.biblioteca.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class LoanService {

    // Todos devem ser FINAL para o Lombok injetar
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookCopyRepository bookCopyRepository;

    public ResponseLoanDTO createLoan(RequestLoanDTO dto) {

        // 1. Procurar User
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Procurar Cópia (Agora o Repository devolve o tipo certo: BookCopy)
        BookCopy bookCopy = bookCopyRepository.findById(dto.bookCopyId())
                .orElseThrow(() -> new RuntimeException("Book Copy not found"));

        // 3. Validação: A cópia está livre?
        if (bookCopy.getStatus() == BookCopyStatus.LOANED) {
            throw new RuntimeException("Esta cópia já está emprestada!");
        }

        // 4. Lógica
        bookCopy.setStatus(BookCopyStatus.LOANED);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBookCopy(bookCopy);
        loan.setLoanDate(LocalDateTime.now());
        loan.setLoanDue(LocalDateTime.now().plusDays(14));

        // 5. Salvar
        loanRepository.save(loan);
        bookCopyRepository.save(bookCopy);

        // 6. Retorno (Garante que os nomes batem com o teu Record ResponseLoanDTO)
        return new ResponseLoanDTO(
                loan.getId(),
                user.getName(),
                bookCopy.getBook().getTitle(),
                loan.getLoanDue()
        );
    }
}

