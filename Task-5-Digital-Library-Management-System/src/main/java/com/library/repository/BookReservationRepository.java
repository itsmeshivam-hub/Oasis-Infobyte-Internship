package com.library.repository;
import com.library.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface BookReservationRepository extends JpaRepository<BookReservation,Long> {
 boolean existsByUserAndBookAndFulfilledFalseAndCancelledFalse(AppUser user, Book book);
 List<BookReservation> findByBookAndFulfilledFalseAndCancelledFalseOrderByReservedAtAsc(Book book);
}