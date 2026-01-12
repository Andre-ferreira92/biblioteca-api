package com.bibliotecaandre.biblioteca.stories.loan.requestloan;

import com.bibliotecaandre.biblioteca.dto.RequestLoanDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseLoanDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/loan")
@Tag(name = "Empréstimos de livro", description = "Gestão dos empréstimos de livros")
public class RequestLoanController {

        private final RequestLoanService requestLoanService;

        @PostMapping
        public ResponseEntity<ResponseLoanDTO> requestLoan(@RequestBody RequestLoanDTO requestLoanDTO) {
            ResponseLoanDTO responseLoanDTO = requestLoanService.createLoan(requestLoanDTO);
            return ResponseEntity.ok(responseLoanDTO);
        }
}
