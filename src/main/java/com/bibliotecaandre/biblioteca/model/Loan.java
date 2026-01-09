package com.bibliotecaandre.biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_copy_id", nullable = false)
    private BookCopy bookCopy;

    @Column(name = "loan_date", nullable = false)
    private LocalDateTime loanDate = LocalDateTime.now();

    @Column(name = "loan_return")
    private LocalDateTime loanReturn ;

    @Column(name = "loan_due", nullable = false)
    private LocalDateTime loanDue ;

}
