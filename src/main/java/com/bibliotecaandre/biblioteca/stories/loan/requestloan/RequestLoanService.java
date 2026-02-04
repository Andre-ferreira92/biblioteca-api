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

    private static final int MAX_ACTIVE_LOANS_PER_USER = 3;
    private static final int LOAN_DURATION_DAYS = 30;

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public ResponseLoanDTO createLoan(RequestLoanDTO dto) {
        log.info("Initiating book loan request. UserID: {}, PhysicalBookID: {}",
                dto.userId(), dto.physicalBookId());

        User user = findUserById(dto.userId());
        PhysicalBook physicalBook = findPhysicalBookById(dto.physicalBookId());

        validateUserSuspension(user);
        validateDuplicateBookLoan(dto.userId(), physicalBook.getBook().getId());
        validateUserLoanLimit(dto.userId());
        validateBookAvailability(physicalBook);

        Loan loan = createLoanEntity(user, physicalBook);

        saveLoanAndBook(loan, physicalBook);

        log.info("Loan created successfully! ID: {} for user: {}", loan.getId(), user.getName());

        return buildLoanResponseDTO(loan);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private PhysicalBook findPhysicalBookById(Long physicalBookId) {
        return physicalBookRepository.findById(physicalBookId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private void validateUserSuspension(User user) {
        if (user.getBlockedUntil() != null && user.getBlockedUntil().isAfter(LocalDateTime.now())) {
            log.warn("Request denied: User {} is suspended until {}", user.getId(), user.getBlockedUntil());
            throw new BusinessRuleException("Request denied. User is suspended until: " + user.getBlockedUntil());
        }
    }

    private void validateDuplicateBookLoan(Long userId, Long bookId) {
        boolean hasSameBook = loanRepository
                .existsByUserIdAndPhysicalBook_Book_IdAndLoanReturnIsNull(userId, bookId);
        if (hasSameBook) {
            throw new BusinessRuleException("The user has already requested this book");
        }
    }

    private void validateUserLoanLimit(Long userId) {
        int activeLoans = loanRepository.countByUserIdAndLoanReturnIsNull(userId);
        if (activeLoans >= MAX_ACTIVE_LOANS_PER_USER) {
            log.warn("Limit reached: User {} already has {} active loans", userId, activeLoans);
            throw new BusinessRuleException("User has already reached the limit of " + MAX_ACTIVE_LOANS_PER_USER + " loans");
        }
    }

    private void validateBookAvailability(PhysicalBook physicalBook) {
        if (physicalBook.getStatus() == PhysicalBookStatus.LOANED) {
            throw new BusinessRuleException("No copies available");
        }
    }

    private Loan createLoanEntity(User user, PhysicalBook physicalBook) {
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setPhysicalBook(physicalBook);
        loan.setLoanDate(LocalDateTime.now());
        loan.setLoanDue(LocalDateTime.now().plusDays(LOAN_DURATION_DAYS));
        physicalBook.setStatus(PhysicalBookStatus.LOANED);
        return loan;
    }

    private void saveLoanAndBook(Loan loan, PhysicalBook physicalBook) {
        loanRepository.save(loan);
        physicalBookRepository.save(physicalBook);
    }

    private ResponseLoanDTO buildLoanResponseDTO(Loan loan) {
        return new ResponseLoanDTO(
                loan.getId(),
                loan.getUser().getName(),
                loan.getPhysicalBook().getBook().getTitle(),
                loan.getLoanDue(),
                loan.getLoanReturn(),
                (loan.getLoanReturn() == null) ? "ACTIVE" : "RETURNED"
        );
    }
}
