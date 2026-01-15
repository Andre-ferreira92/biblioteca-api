package com.bibliotecaandre.biblioteca.stories.loan.requestloan;

import com.bibliotecaandre.biblioteca.dto.RequestLoanDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseLoanDTO;
import com.bibliotecaandre.biblioteca.exceptions.BusinessRuleException;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.BookCopy;
import com.bibliotecaandre.biblioteca.model.BookCopyStatus;
import com.bibliotecaandre.biblioteca.model.Loan;
import com.bibliotecaandre.biblioteca.model.User;
import com.bibliotecaandre.biblioteca.repository.BookCopyRepository;
import com.bibliotecaandre.biblioteca.repository.LoanRepository;
import com.bibliotecaandre.biblioteca.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor

public class RequestLoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookCopyRepository bookCopyRepository;

    @Transactional
    public ResponseLoanDTO createLoan(RequestLoanDTO dto) {

        User user = userRepository.findById(dto.userId())
                .orElseThrow(ResourceNotFoundException::new);

        if (user.getBlockedUntil() != null && user.getBlockedUntil().isAfter(LocalDateTime.now())) {
            throw new BusinessRuleException("Pedido negado. O utilizador está suspenso até: " + user.getBlockedUntil());
        }

        BookCopy bookCopy = bookCopyRepository.findById(dto.bookCopyId())
                .orElseThrow(ResourceNotFoundException::new);

        //Verifica se user quer requesitar o mesmo livro 2x
        Long bookId = bookCopy.getBook().getId();
        boolean hasSameBook = loanRepository.existsByUserIdAndBookCopyBookIdAndLoanReturnIsNull(dto.userId(), bookId);
        if (hasSameBook) {
            throw new BusinessRuleException("O utilizador ja requesitou este livro");
        }

        //Verifica se user pode ter mais emprestimos
        int activeLoan = loanRepository.countByUserIdAndLoanReturnIsNull(dto.userId());
        if (activeLoan >= 3) {
            throw new BusinessRuleException("O utilizador já tem 3 empréstimos");
        }



        if (bookCopy.getStatus() == BookCopyStatus.LOANED) {
            throw new BusinessRuleException("Sem cópias disponíveis");
        }

        //cria empréstimo
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBookCopy(bookCopy);
        loan.setLoanDate(LocalDateTime.now());
        loan.setLoanDue(LocalDateTime.now().plusDays(30));
        bookCopy.setStatus(BookCopyStatus.LOANED);

        loanRepository.save(loan);
        bookCopyRepository.save(bookCopy);

        //Devolve na DTO
        return new ResponseLoanDTO(
                loan.getId(),
                loan.getUser().getName(),
                loan.getBookCopy().getBook().getTitle(),
                loan.getLoanDue(),
                loan.getLoanReturn(),
                (loan.getLoanReturn() == null) ? "ATIVO" : "ENTREGUE");
    }
}
