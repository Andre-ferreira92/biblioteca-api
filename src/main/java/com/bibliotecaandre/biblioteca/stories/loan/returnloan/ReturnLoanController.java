package com.bibliotecaandre.biblioteca.stories.loan.returnloan;

import com.bibliotecaandre.biblioteca.dto.RequestLoanDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
@Tag(name = "Loan", description = "Gestão dos empréstimos de livros")
@AllArgsConstructor
public class ReturnLoanController {
    private ReturnLoanService returnLoanService;

    @PatchMapping("/{id}/return")
    public ResponseEntity<Void> returnLoan(@PathVariable Long id){
        returnLoanService.returnLoan(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
