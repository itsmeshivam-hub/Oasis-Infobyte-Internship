package com.library.repository;
import com.library.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface FineRepository extends JpaRepository<Fine,Long> { List<Fine> findByUser(AppUser user); }