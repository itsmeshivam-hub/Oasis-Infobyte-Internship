package com.library.repository;
import com.library.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface LoanRepository extends JpaRepository<Loan,Long> {
 List<Loan> findByUserAndReturnedFalse(AppUser user); List<Loan> findByReturnedFalse(); List<Loan> findByUser(AppUser user);
 boolean existsByUserAndBookAndReturnedFalse(AppUser user, Book book);
}