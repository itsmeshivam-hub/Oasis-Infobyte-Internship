package com.library.repository;
import com.library.model.AppUser; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
 Optional<AppUser> findByUsername(String username); Optional<AppUser> findByEmail(String email);
}