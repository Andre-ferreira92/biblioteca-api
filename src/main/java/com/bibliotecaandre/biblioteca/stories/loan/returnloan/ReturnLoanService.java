package com.bibliotecaandre.biblioteca.stories.loan.returnloan;

import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.PhysicalBookStatus;
import com.bibliotecaandre.biblioteca.model.Loan;
import com.bibliotecaandre.biblioteca.repository.LoanRepository;
import com.bibliotecaandre.biblioteca.repository.UserRepository;
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
    private final UserRepository userRepository;

    // Constants para evitar hardcoded values
    private static final int OVERDUE_LIMIT_FOR_SUSPENSION = 2;
    private static final int SUSPENSION_DAYS = 15;

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public void returnLoan(Long id) {
        log.info("Starting book return process");

        Loan loan = findLoanById(id);
        processLoanReturn(loan);
        checkAndApplyUserSuspension(loan.getUser().getId());

        log.info("Book return process for ID: {} completed", loan.getId());
    }

    private Loan findLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private void processLoanReturn(Loan loan) {
        loan.setLoanReturn(LocalDateTime.now());
        loan.getPhysicalBook().setStatus(PhysicalBookStatus.AVAILABLE);
        loanRepository.save(loan);
    }

    private void checkAndApplyUserSuspension(Long userId) {
        List<Loan> historicLoans = loanRepository.findByUserIdAndLoanReturnIsNotNull(userId);
        long overdueCount = countOverdueLoans(historicLoans);

        if (overdueCount >= OVERDUE_LIMIT_FOR_SUSPENSION) {
            applyUserSuspension(userId, overdueCount);
        }
    }

    private long countOverdueLoans(List<Loan> loans) {
        return loans.stream()
                .filter(loan -> loan.getLoanReturn().isAfter(loan.getLoanDue()))
                .count();
    }

    private void applyUserSuspension(Long userId, long overdueCount) {
        LocalDateTime blockedUntil = LocalDateTime.now().plusDays(SUSPENSION_DAYS);
        log.warn("User {} blocked until {} due to {} overdue returns",
                userId, blockedUntil, overdueCount);

        userRepository.findById(userId).ifPresent(user -> {
            user.setBlockedUntil(blockedUntil);
            userRepository.save(user);
        });
    }
}
