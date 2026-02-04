package com.bibliotecaandre.biblioteca.stories.loan.requestloan;

import com.bibliotecaandre.biblioteca.dto.RequestLoanDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseLoanDTO;
import com.bibliotecaandre.biblioteca.exceptions.BusinessRuleException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestLoanServiceTest {

    @Mock
    LoanRepository loanRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PhysicalBookRepository physicalBookRepository;
    @InjectMocks
    RequestLoanService requestLoanService;

    @Test
    void createLoanSuccess() {

        RequestLoanDTO requestLoanDTO = new RequestLoanDTO(1L, 1L);

        User user = new User();
        user.setId(1L);
        user.setName("User");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book");

        PhysicalBook physicalBook = new PhysicalBook();
        physicalBook.setId(1L);
        physicalBook.setInventoryCode("INV-001");
        physicalBook.setStatus(PhysicalBookStatus.AVAILABLE);
        physicalBook.setBook(book);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(physicalBookRepository.findById(1L)).thenReturn(Optional.of(physicalBook));
        when(physicalBookRepository.save(any(PhysicalBook.class))).thenReturn(physicalBook);
        when(loanRepository.existsByUserIdAndPhysicalBook_Book_IdAndLoanReturnIsNull(1L, 1L)).thenReturn(false);
        when(loanRepository.countByUserIdAndLoanReturnIsNull(1L)).thenReturn(0);

        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> {
            Loan saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        ResponseLoanDTO responseLoanDTO = requestLoanService.createLoan(requestLoanDTO);

        assertEquals(1L, responseLoanDTO.id());
        assertEquals("User", responseLoanDTO.userName());
        assertEquals("Book", responseLoanDTO.bookTitle());
        assertNull(responseLoanDTO.returnDate());
        assertEquals("ACTIVE", responseLoanDTO.Status());

        verify(userRepository, times(1)).findById(1L);
        verify(physicalBookRepository, times(1)).findById(1L);
        verify(physicalBookRepository, times(1)).save(any(PhysicalBook.class));
        verify(loanRepository, times(1)).existsByUserIdAndPhysicalBook_Book_IdAndLoanReturnIsNull(1L, 1L);
        verify(loanRepository, times(1)).countByUserIdAndLoanReturnIsNull(1L);
        verify(loanRepository, times(1)).save(any(Loan.class));
        verifyNoMoreInteractions(userRepository, physicalBookRepository, loanRepository);
    }

    @Test
    void createLoanWhenUserDoesNotExistThrowsException(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> requestLoanService.createLoan(new RequestLoanDTO(1L, 1L)));

        verify(userRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(userRepository, physicalBookRepository, loanRepository);
    }

    @Test
    void createLoanWhenUserIsBlockedThrowsException(){

        User user = new User();
        user.setId(1L);
        user.setName("User");
        user.setBlockedUntil(LocalDateTime.now().plusDays(1));

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book");

        PhysicalBook physicalBook = new PhysicalBook();
        physicalBook.setId(1L);
        physicalBook.setBook(book);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(physicalBookRepository.findById(1L)).thenReturn(Optional.of(physicalBook));  // ✅ Mock completo

        assertThrows(BusinessRuleException.class, () -> requestLoanService.createLoan(new RequestLoanDTO(1L, 1L)));

        verify(userRepository, times(1)).findById(1L);
        verify(physicalBookRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(loanRepository, physicalBookRepository, userRepository);
    }

    @Test
    void createLoanWhenPhysicalBookDoesNotExistThrowsException(){

        User user = new User();
        user.setId(1L);
        user.setName("User");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(physicalBookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> requestLoanService.createLoan(new RequestLoanDTO(1L, 1L)));

        verify(physicalBookRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(physicalBookRepository, userRepository, loanRepository);
    }

    @Test
    void createLoanWhenLoanAlreadyExistsThrowsException() {
        User user = new User();
        user.setId(1L);
        user.setName("User");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book");

        PhysicalBook physicalBook = new PhysicalBook();
        physicalBook.setId(1L);
        physicalBook.setBook(book);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(physicalBookRepository.findById(1L)).thenReturn(Optional.of(physicalBook));
        when(loanRepository.existsByUserIdAndPhysicalBook_Book_IdAndLoanReturnIsNull(1L, 1L)).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> requestLoanService.createLoan(new RequestLoanDTO(1L, 1L)));

        verify(loanRepository, times(1)).existsByUserIdAndPhysicalBook_Book_IdAndLoanReturnIsNull(1L, 1L);
        verify(userRepository, times(1)).findById(1L);
        verify(physicalBookRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(loanRepository, userRepository, physicalBookRepository);
    }

    @Test
    void createLoanWhenUserHasThreeLoansThrowsException() {

        User user = new User();
        user.setId(1L);
        user.setName("User");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book");

        PhysicalBook physicalBook = new PhysicalBook();
        physicalBook.setId(1L);
        physicalBook.setBook(book);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(physicalBookRepository.findById(1L)).thenReturn(Optional.of(physicalBook));
        when(loanRepository.existsByUserIdAndPhysicalBook_Book_IdAndLoanReturnIsNull(1L, 1L)).thenReturn(false);
        when(loanRepository.countByUserIdAndLoanReturnIsNull(1L)).thenReturn(3);  // ✅ 3 deve falhar

        assertThrows(BusinessRuleException.class, () -> requestLoanService.createLoan(new RequestLoanDTO(1L, 1L)));

        verify(userRepository, times(1)).findById(1L);
        verify(physicalBookRepository, times(1)).findById(1L);
        verify(loanRepository, times(1)).existsByUserIdAndPhysicalBook_Book_IdAndLoanReturnIsNull(1L, 1L);
        verify(loanRepository, times(1)).countByUserIdAndLoanReturnIsNull(1L);
        verifyNoMoreInteractions(userRepository, physicalBookRepository, loanRepository);
    }
}


