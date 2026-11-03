package com.library.service;

import com.library.model.*;
import com.library.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class LibraryService {
    private static final BigDecimal FINE_PER_DAY = new BigDecimal("5.00");
    private final BookRepository books; private final LoanRepository loans;
    private final FineRepository fines; private final BookReservationRepository reservations;
    private final AppUserRepository users;

    public LibraryService(BookRepository books, LoanRepository loans, FineRepository fines,
                          BookReservationRepository reservations, AppUserRepository users) {
        this.books=books; this.loans=loans; this.fines=fines; this.reservations=reservations; this.users=users;
    }

    @Transactional
    public Loan issueBook(Long bookId, String username) {
        Book book=books.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        AppUser user=users.findByUsername(username).orElseThrow();
        if (book.getAvailableQuantity()<1) throw new IllegalStateException("No copies are currently available. You can reserve this book.");
        if (loans.existsByUserAndBookAndReturnedFalse(user,book)) throw new IllegalStateException("You already have this book issued.");
        book.setAvailableQuantity(book.getAvailableQuantity()-1); books.save(book);
        Loan loan=new Loan(); loan.setBook(book); loan.setUser(user); loan.setIssueDate(LocalDate.now()); loan.setDueDate(LocalDate.now().plusDays(14));
        return loans.save(loan);
    }

    @Transactional
    public BigDecimal returnBook(Long loanId, String username) {
        Loan loan=loans.findById(loanId).orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        if (!loan.getUser().getUsername().equals(username)) throw new IllegalStateException("This loan does not belong to you.");
        if (loan.isReturned()) throw new IllegalStateException("Book already returned.");
        LocalDate today=LocalDate.now(); long days=loan.overdueDays(today);
        loan.setReturned(true); loan.setReturnDate(today); loans.save(loan);
        Book book=loan.getBook(); book.setAvailableQuantity(book.getAvailableQuantity()+1); books.save(book);
        BigDecimal amount=FINE_PER_DAY.multiply(BigDecimal.valueOf(days));
        if (amount.signum()>0) {
            Fine fine=new Fine(); fine.setLoan(loan); fine.setUser(loan.getUser()); fine.setAmount(amount); fines.save(fine);
        }
        // Promote the oldest active reservation by marking it fulfilled. Admin/user can then issue when copy is available.
        List<BookReservation> waiting=reservations.findByBookAndFulfilledFalseAndCancelledFalseOrderByReservedAtAsc(book);
        if (!waiting.isEmpty()) { BookReservation first=waiting.get(0); first.setFulfilled(true); reservations.save(first); }
        return amount;
    }

    @Transactional
    public BookReservation reserveBook(Long bookId, String username) {
        Book book=books.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        AppUser user=users.findByUsername(username).orElseThrow();
        if (book.getAvailableQuantity()>0) throw new IllegalStateException("A copy is available; issue it instead of reserving.");
        if (reservations.existsByUserAndBookAndFulfilledFalseAndCancelledFalse(user,book)) throw new IllegalStateException("You already have an active reservation.");
        BookReservation r=new BookReservation(); r.setBook(book); r.setUser(user); return reservations.save(r);
    }
}
