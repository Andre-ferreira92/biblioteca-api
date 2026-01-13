package com.bibliotecaandre.biblioteca.stories.loan.returnloan;

import com.bibliotecaandre.biblioteca.model.BookCopyStatus;
import com.bibliotecaandre.biblioteca.model.Loan;
import com.bibliotecaandre.biblioteca.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class ReturnLoanService {

    private final LoanRepository loanRepository;

    @Transactional
    public void returnLoan(Long id) {

        //Encontrar id do loan
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));

        //alterar estado do loan e add data de retorno
        loan.setLoanReturn(LocalDateTime.now());
        loan.getBookCopy().setStatus(BookCopyStatus.AVAILABLE);

    }
}
