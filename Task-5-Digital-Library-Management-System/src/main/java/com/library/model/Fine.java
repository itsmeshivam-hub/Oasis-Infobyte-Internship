package com.library.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Fine {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @OneToOne(optional=false) private Loan loan;
    @ManyToOne(optional=false) private AppUser user;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal amount;
    private boolean paid = false;
    private LocalDate createdDate = LocalDate.now();

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Loan getLoan(){return loan;} public void setLoan(Loan loan){this.loan=loan;}
    public AppUser getUser(){return user;} public void setUser(AppUser user){this.user=user;}
    public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal amount){this.amount=amount;}
    public boolean isPaid(){return paid;} public void setPaid(boolean paid){this.paid=paid;}
    public LocalDate getCreatedDate(){return createdDate;} public void setCreatedDate(LocalDate createdDate){this.createdDate=createdDate;}
}
