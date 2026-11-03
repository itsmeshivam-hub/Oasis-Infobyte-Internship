package com.library.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BookReservation {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private AppUser user;
    @ManyToOne(optional=false) private Book book;
    private LocalDateTime reservedAt = LocalDateTime.now();
    private boolean fulfilled = false;
    private boolean cancelled = false;
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public AppUser getUser(){return user;} public void setUser(AppUser user){this.user=user;}
    public Book getBook(){return book;} public void setBook(Book book){this.book=book;}
    public LocalDateTime getReservedAt(){return reservedAt;} public void setReservedAt(LocalDateTime reservedAt){this.reservedAt=reservedAt;}
    public boolean isFulfilled(){return fulfilled;} public void setFulfilled(boolean fulfilled){this.fulfilled=fulfilled;}
    public boolean isCancelled(){return cancelled;} public void setCancelled(boolean cancelled){this.cancelled=cancelled;}
}
