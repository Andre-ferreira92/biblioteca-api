package com.bibliotecaandre.biblioteca.stories.loan.returnloan;

import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.BookCopyStatus;
import com.bibliotecaandre.biblioteca.model.Loan;
import com.bibliotecaandre.biblioteca.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ReturnLoanService {

    private final LoanRepository loanRepository;

    @Transactional
    public void returnLoan(Long id) {

        //Encontrar id do loan
        Loan loan = loanRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        //alterar estado do loan e add data de retorno
        loan.setLoanReturn(LocalDateTime.now());
        loan.getBookCopy().setStatus(BookCopyStatus.AVAILABLE);

        //Verificar se user tem atrasos e se tiver aplicar bloqueio
        List<Loan> historicLoans = loanRepository.findByUserIdAndLoanReturnIsNotNull(loan.getUser().getId());

        long totalDue = historicLoans.stream()
                .filter(loans -> loans.getLoanReturn().isAfter(loans.getLoanDue()))
                .count();

        if (totalDue >= 2) {
            LocalDateTime blocked =  LocalDateTime.now().plusDays(15);
            loan.getUser().setBlockedUntil(blocked);
        }
    }
}
