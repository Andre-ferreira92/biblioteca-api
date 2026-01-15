package com.bibliotecaandre.biblioteca.repository;

import com.bibliotecaandre.biblioteca.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    int countByUserIdAndLoanReturnIsNull(Long userId);
    boolean existsByUserIdAndBookCopyBookIdAndLoanReturnIsNull(Long userId, Long bookId);
}
