package com.bibliotecaandre.biblioteca.stories.loan.returnloan;

import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.PhysicalBookStatus;
import com.bibliotecaandre.biblioteca.model.Loan;
import com.bibliotecaandre.biblioteca.repository.LoanRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class ReturnLoanService {

    private final LoanRepository loanRepository;

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public void returnLoan(Long id) {

        //Encontrar id do loan
        log.info("Inicio de processo de devolução de livro");
        Loan loan = loanRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        //alterar estado do loan e add data de retorno
        loan.setLoanReturn(LocalDateTime.now());
        loan.getPhysicalBook().setStatus(PhysicalBookStatus.AVAILABLE);
        loanRepository.save(loan);
        log.info("Processo de devolução de livro com id: {} finalizado", loan.getId());

        //Verificar se user tem atrasos e se tiver aplicar bloqueio
        List<Loan> historicLoans = loanRepository.findByUserIdAndLoanReturnIsNotNull(loan.getUser().getId());

        long totalDue = historicLoans.stream()
                .filter(loans -> loans.getLoanReturn().isAfter(loans.getLoanDue()))
                .count();

        if (totalDue >= 2) {
            LocalDateTime blocked =  LocalDateTime.now().plusDays(15);
            log.warn("Utilizador {} bloqueado até {} por acumular {} atrasos", loan.getUser().getId(), blocked, totalDue);
            loan.getUser().setBlockedUntil(blocked);
            loanRepository.save(loan);

        }
    }
}
