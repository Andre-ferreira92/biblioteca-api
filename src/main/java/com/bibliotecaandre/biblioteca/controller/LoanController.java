package com.bibliotecaandre.biblioteca.controller;

import com.bibliotecaandre.biblioteca.dto.RequestLoanDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseLoanDTO;
import com.bibliotecaandre.biblioteca.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<ResponseLoanDTO> requestLoan(@RequestBody RequestLoanDTO requestLoanDTO) {
        ResponseLoanDTO responseLoanDTO = loanService.createLoan(requestLoanDTO);
        return ResponseEntity.ok(responseLoanDTO);
    }

    @GetMapping
    public ResponseEntity<List<ResponseLoanDTO>> getLoans() {
        List<ResponseLoanDTO> responseLoanDTOS = loanService.getAllLoans();
        return new ResponseEntity<>(responseLoanDTOS, HttpStatus.OK);
    }
}
