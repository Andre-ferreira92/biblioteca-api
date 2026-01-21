package com.bibliotecaandre.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
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
    private PhysicalBook physicalBook;

    @Column(name = "loan_date", nullable = false)
    private LocalDateTime loanDate = LocalDateTime.now();

    @Column(name = "loan_return")
    private LocalDateTime loanReturn ;

    @Column(name = "loan_due", nullable = false)
    private LocalDateTime loanDue ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return id != null && id.equals(loan.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
