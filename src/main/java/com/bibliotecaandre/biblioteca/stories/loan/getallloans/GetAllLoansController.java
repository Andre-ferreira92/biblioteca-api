package com.bibliotecaandre.biblioteca.stories.loan.getallloans;

import com.bibliotecaandre.biblioteca.dto.ResponseLoanDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("loan")
@Tag(name = "Loan", description = "Gestão dos empréstimos de livros")
@RestController
@AllArgsConstructor
public class GetAllLoansController {

    private final GetAllLoansService getAllLoansService;

    @GetMapping
    public ResponseEntity<List<ResponseLoanDTO>> getLoans() {
        List<ResponseLoanDTO> responseLoanDTOS = getAllLoansService.getAllLoans();
        return new ResponseEntity<>(responseLoanDTOS, HttpStatus.OK);
    }
}
