package com.library.repository;
import com.library.model.LibraryQuery; import org.springframework.data.jpa.repository.JpaRepository;
public interface LibraryQueryRepository extends JpaRepository<LibraryQuery,Long> {}