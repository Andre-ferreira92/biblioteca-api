package com.bibliotecaandre.biblioteca.stories.loan.getallloans;

import com.bibliotecaandre.biblioteca.dto.ResponseLoanDTO;
import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.Loan;
import com.bibliotecaandre.biblioteca.model.PhysicalBook;
import com.bibliotecaandre.biblioteca.model.User;
import com.bibliotecaandre.biblioteca.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllLoansServiceTest {

    @Mock
    LoanRepository loanRepository;
    @InjectMocks
    GetAllLoansService getAllLoansService;

    @Test
    void getAllLoansSuccess() {

        User user = new User();
        user.setId(1L);
        user.setName("User");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book");

        PhysicalBook physicalBook = new PhysicalBook();
        physicalBook.setId(1L);
        physicalBook.setBook(book);

        Loan loan = new Loan();
        loan.setId(1L);
        loan.setUser(user);
        loan.setPhysicalBook(physicalBook);
        loan.setLoanDate(LocalDateTime.now());
        loan.setLoanDue(LocalDateTime.now().plusDays(7));
        loan.setLoanReturn(LocalDateTime.now().plusDays(3));

        when(loanRepository.findAll()).thenReturn(List.of(loan));

        List<ResponseLoanDTO> responseLoanDTO = getAllLoansService.getAllLoans();

        assertEquals(1, responseLoanDTO.size());
        assertEquals(1L, responseLoanDTO.get(0).id());
        assertEquals("User", responseLoanDTO.get(0).userName());
        assertEquals("Book", responseLoanDTO.get(0).bookTitle());

        assertEquals(loan.getLoanDue(), responseLoanDTO.get(0).dueDate());
        assertEquals(loan.getLoanReturn(), responseLoanDTO.get(0).returnDate());
        assertEquals("ENTREGUE", responseLoanDTO.get(0).Status());

        verify(loanRepository, times(1)).findAll();
        verifyNoMoreInteractions(loanRepository);
    }

    @Test
    void getAllLoansWhenReturnIsEmpty(){

        when(loanRepository.findAll()).thenReturn(List.of());

        List<ResponseLoanDTO> responseLoanDTO = getAllLoansService.getAllLoans();

        assertEquals(0, responseLoanDTO.size());

        verify(loanRepository, times(1)).findAll();
        verifyNoMoreInteractions(loanRepository);

    }
}