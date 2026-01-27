package com.bibliotecaandre.biblioteca.stories.loan.getallloans;

import com.bibliotecaandre.biblioteca.dto.ResponseLoanDTO;
import com.bibliotecaandre.biblioteca.model.Loan;
import com.bibliotecaandre.biblioteca.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllLoansService {

    private final LoanRepository loanRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public List<ResponseLoanDTO> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();

        return loans.stream()
                .map(copy -> new ResponseLoanDTO(
                        copy.getId(),
                        copy.getUser().getName(),
                        copy.getPhysicalBook().getBook().getTitle(),
                        copy.getLoanDue(),
                        copy.getLoanReturn(),
                        (copy.getLoanReturn() == null) ? "ATIVO" : "ENTREGUE"
                ))

                .toList();
    }
}
