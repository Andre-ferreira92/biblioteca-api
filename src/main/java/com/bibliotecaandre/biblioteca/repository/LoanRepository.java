package com.bibliotecaandre.biblioteca.repository;

import com.bibliotecaandre.biblioteca.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.ListResourceBundle;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    int countByUserIdAndLoanReturnIsNull(Long userId);
    boolean existsByUserIdAndBookCopyBookIdAndLoanReturnIsNull(Long userId, Long bookId);
    List<Loan> findByUserIdAndLoanReturnIsNotNull(Long userId);
    List<Loan> findByLoanReturnIsNull();
}
