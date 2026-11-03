package com.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Loan {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private AppUser user;
    @ManyToOne(optional=false) private Book book;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean returned;

    public long overdueDays(LocalDate onDate) {
        LocalDate end = returned && returnDate != null ? returnDate : onDate;
        return end.isAfter(dueDate) ? ChronoUnit.DAYS.between(dueDate, end) : 0;
    }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public AppUser getUser(){return user;} public void setUser(AppUser user){this.user=user;}
    public Book getBook(){return book;} public void setBook(Book book){this.book=book;}
    public LocalDate getIssueDate(){return issueDate;} public void setIssueDate(LocalDate issueDate){this.issueDate=issueDate;}
    public LocalDate getDueDate(){return dueDate;} public void setDueDate(LocalDate dueDate){this.dueDate=dueDate;}
    public LocalDate getReturnDate(){return returnDate;} public void setReturnDate(LocalDate returnDate){this.returnDate=returnDate;}
    public boolean isReturned(){return returned;} public void setReturned(boolean returned){this.returned=returned;}
}
