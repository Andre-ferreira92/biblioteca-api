package com.bibliotecaandre.biblioteca.service;

import com.bibliotecaandre.biblioteca.dto.RequestLoanDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseLoanDTO;
import com.bibliotecaandre.biblioteca.model.BookCopy;
import com.bibliotecaandre.biblioteca.model.BookCopyStatus;
import com.bibliotecaandre.biblioteca.model.Loan;
import com.bibliotecaandre.biblioteca.model.User;
import com.bibliotecaandre.biblioteca.repository.BookCopyRepository;
import com.bibliotecaandre.biblioteca.repository.LoanRepository;
import com.bibliotecaandre.biblioteca.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookCopyRepository bookCopyRepository;

    public ResponseLoanDTO createLoan(RequestLoanDTO dto) {

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        BookCopy bookCopy = bookCopyRepository.findById(dto.bookCopyId())
                .orElseThrow(() -> new RuntimeException("Book copy not found"));

        if (bookCopy.getStatus() == BookCopyStatus.LOANED) {
            throw new RuntimeException("Book not available");
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBookCopy(bookCopy);
        loan.setLoanDate(LocalDateTime.now());
        loan.setLoanDue(LocalDateTime.now().plusDays(30));
        bookCopy.setStatus(BookCopyStatus.LOANED);

        loanRepository.save(loan);
        bookCopyRepository.save(bookCopy);

        ResponseLoanDTO responseLoanDTO = new ResponseLoanDTO(
                loan.getId(),
                loan.getUser().getName(),
                loan.getUser().getEmail(),
                loan.getLoanDue());
        return responseLoanDTO;
    }

    public List<ResponseLoanDTO> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();

        return loans.stream()
                .map(copy -> new ResponseLoanDTO(
                        copy.getId(),
                        copy.getUser().getName(),
                        copy.getUser().getEmail(),
                        copy.getLoanDue()
                ))

                .toList();
    }

}


