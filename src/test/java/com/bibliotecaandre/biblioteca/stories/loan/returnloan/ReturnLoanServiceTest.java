package com.bibliotecaandre.biblioteca.stories.loan.returnloan;

import com.bibliotecaandre.biblioteca.dto.RequestLoanDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseLoanDTO;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.*;
import com.bibliotecaandre.biblioteca.repository.LoanRepository;
import com.bibliotecaandre.biblioteca.repository.PhysicalBookRepository;
import com.bibliotecaandre.biblioteca.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReturnLoanServiceTest {

    @Mock
    LoanRepository loanRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    ReturnLoanService returnLoanService;

    @Test
    void returnLoanSuccess() {

        User user = new User();
        user.setId(1L);
        user.setName("User");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book");

        PhysicalBook physicalBook = new PhysicalBook();
        physicalBook.setId(1L);
        physicalBook.setInventoryCode("INV-001");
        physicalBook.setStatus(PhysicalBookStatus.LOANED);
        physicalBook.setBook(book);

        Loan loan = new Loan();
        loan.setId(1L);
        loan.setUser(user);
        loan.setPhysicalBook(physicalBook);
        loan.setLoanDue(LocalDateTime.now().plusDays(3));
        loan.setLoanReturn(LocalDateTime.now());

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanRepository.findByUserIdAndLoanReturnIsNotNull(1L)).thenReturn(List.of());

        returnLoanService.returnLoan(1L);

        verify(loanRepository, times(1)).findById(1L);
        verify(loanRepository, times(1)).save(any(Loan.class));
        verify(loanRepository, times(1)).findByUserIdAndLoanReturnIsNotNull(1L);
        verifyNoMoreInteractions(loanRepository, userRepository);
    }

    @Test
    void returnLoanWhenNotFoundThrowException() {

       when(loanRepository.findById(1L)).thenReturn(Optional.empty());

       assertThrows(ResourceNotFoundException.class, () -> returnLoanService.returnLoan(1L));

       verify(loanRepository, times(1)).findById(1L);

       verifyNoMoreInteractions(loanRepository, userRepository);
    }

    @Test
    void returnLoanWhenUserHasDelaysShouldBlockUser() {

        User user = new User();
        user.setId(1L);
        user.setName("User");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book");

        PhysicalBook physicalBook = new PhysicalBook();
        physicalBook.setId(1L);
        physicalBook.setBook(book);

        Loan currentLoan = new Loan();
        currentLoan.setId(1L);
        currentLoan.setUser(user);
        currentLoan.setPhysicalBook(physicalBook);
        currentLoan.setLoanDue(LocalDateTime.now().plusDays(7));
        currentLoan.setLoanReturn(null);

        Loan delayedLoan1 = new Loan();
        delayedLoan1.setId(2L);
        delayedLoan1.setUser(user);
        delayedLoan1.setLoanDue(LocalDateTime.now().minusDays(10));
        delayedLoan1.setLoanReturn(LocalDateTime.now().minusDays(5));

        Loan delayedLoan2 = new Loan();
        delayedLoan2.setId(3L);
        delayedLoan2.setUser(user);
        delayedLoan2.setLoanDue(LocalDateTime.now().minusDays(8));
        delayedLoan2.setLoanReturn(LocalDateTime.now().minusDays(2));

        when(loanRepository.findById(1L)).thenReturn(Optional.of(currentLoan));
        when(loanRepository.save(any(Loan.class))).thenReturn(currentLoan);
        when(loanRepository.findByUserIdAndLoanReturnIsNotNull(1L)).thenReturn(List.of(delayedLoan1, delayedLoan2));
        when(userRepository.save(any(User.class))).thenReturn(user);

        returnLoanService.returnLoan(1L);

        verify(loanRepository, times(1)).findById(1L);
        verify(loanRepository, times(1)).save(any(Loan.class));
        verify(loanRepository, times(1)).findByUserIdAndLoanReturnIsNotNull(1L);
        verify(userRepository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(loanRepository, userRepository);
    }

}