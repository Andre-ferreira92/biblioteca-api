package com.bibliotecaandre.biblioteca.common.email;

import com.bibliotecaandre.biblioteca.model.Loan;
import com.bibliotecaandre.biblioteca.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class EmailService {

    private final LoanRepository loanRepository;
    //cron - 0 0 9 * * * --> para envio diario as 9h
    @Scheduled(cron = "0 * * * * *")
    public void checkLateLoans() {

        //procura por empréstimos que a data de retorno seja null
        List<Loan> activeLoans = loanRepository.findByLoanReturnIsNull();

        //Define a variável now com a data de agora
        LocalDateTime now = LocalDateTime.now();

        //Percorre a lista de empréstimos que sao null
        List<Loan> lateLoans = activeLoans.stream()
                .filter(loan -> loan.getLoanDue().isBefore(now))
                .toList();

        //Para cada empréstimo atrasado cria a msg
        lateLoans.forEach(loan -> {
            System.out.println("AVISO: O utilizador " + loan.getUser().getName() + " está atrasado a devolver o livro " + loan.getPhysicalBook().getBook().getTitle());
        });
    }
}

