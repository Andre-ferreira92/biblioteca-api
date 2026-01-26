package com.bibliotecaandre.biblioteca.repository;

import com.bibliotecaandre.biblioteca.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    int countByUserIdAndLoanReturnIsNull(Long userId);
    boolean existsByUserIdAndPhysicalBook_Book_IdAndLoanReturnIsNull(Long userId, Long bookId);
    List<Loan> findByUserIdAndLoanReturnIsNotNull(Long userId);
    List<Loan> findByLoanReturnIsNull();
}
