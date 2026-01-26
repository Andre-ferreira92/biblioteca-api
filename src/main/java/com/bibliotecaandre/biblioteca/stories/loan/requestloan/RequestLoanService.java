package com.bibliotecaandre.biblioteca.stories.loan.requestloan;

import com.bibliotecaandre.biblioteca.dto.RequestLoanDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseLoanDTO;
import com.bibliotecaandre.biblioteca.exceptions.BusinessRuleException;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.PhysicalBook;
import com.bibliotecaandre.biblioteca.model.PhysicalBookStatus;
import com.bibliotecaandre.biblioteca.model.Loan;
import com.bibliotecaandre.biblioteca.model.User;
import com.bibliotecaandre.biblioteca.repository.PhysicalBookRepository;
import com.bibliotecaandre.biblioteca.repository.LoanRepository;
import com.bibliotecaandre.biblioteca.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class RequestLoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final PhysicalBookRepository physicalBookRepository;

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public ResponseLoanDTO createLoan(RequestLoanDTO dto) {
        log.info("Iniciando requisição de livro. UserID: {}, PhysicalBookID: {}", dto.userId(), dto.physicalBookId());
        User user = userRepository.findById(dto.userId())
                .orElseThrow(ResourceNotFoundException::new);


        // Impede o empréstimo se o utilizador ainda estiver dentro do período de suspensão
        if (user.getBlockedUntil() != null && user.getBlockedUntil().isAfter(LocalDateTime.now())) {
            log.warn("Pedido negado: Utilizador {} suspenso até {}", user.getId(), user.getBlockedUntil());
            throw new BusinessRuleException("Pedido negado. O utilizador está suspenso até: " + user.getBlockedUntil());
        }

        PhysicalBook physicalBook = physicalBookRepository.findById(dto.physicalBookId())
                .orElseThrow(ResourceNotFoundException::new);

        //Verifica se user quer requesitar o mesmo livro 2x
        Long bookId = physicalBook.getBook().getId();
        boolean hasSameBook = loanRepository
                .existsByUserIdAndPhysicalBook_Book_IdAndLoanReturnIsNull(dto.userId(),bookId);
        if (hasSameBook) {
            throw new BusinessRuleException("O utilizador ja requesitou este livro");
        }

        //Verifica se user pode ter mais emprestimos
        int activeLoan = loanRepository.countByUserIdAndLoanReturnIsNull(dto.userId());
        if (activeLoan >= 3) {
            log.warn("Limite atingido: Utilizador {} já possui {} empréstimos ativos", user.getId(), activeLoan);
            throw new BusinessRuleException("O utilizador já tem 3 empréstimos");
        }

        //Muda o Status para LOANED
        if (physicalBook.getStatus() == PhysicalBookStatus.LOANED) {
            throw new BusinessRuleException("Sem cópias disponíveis");
        }

        //Cria empréstimo
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setPhysicalBook(physicalBook);
        loan.setLoanDate(LocalDateTime.now());
        loan.setLoanDue(LocalDateTime.now().plusDays(30));
        physicalBook.setStatus(PhysicalBookStatus.LOANED);

        loanRepository.save(loan);
        physicalBookRepository.save(physicalBook);

        log.info("Empréstimo criado com sucesso! ID: {} para o utilizador: {}", loan.getId(), user.getName());

        //Devolve na DTO o empréstimo
        return new ResponseLoanDTO(
                loan.getId(),
                loan.getUser().getName(),
                loan.getPhysicalBook().getBook().getTitle(),
                loan.getLoanDue(),
                loan.getLoanReturn(),
                (loan.getLoanReturn() == null) ? "ATIVO" : "ENTREGUE");
    }
}
