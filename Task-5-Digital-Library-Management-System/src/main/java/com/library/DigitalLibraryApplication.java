package com.library;

import com.library.model.AppUser;
import com.library.model.Role;
import com.library.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DigitalLibraryApplication {
    public static void main(String[] args) { SpringApplication.run(DigitalLibraryApplication.class, args); }

    @Bean
    CommandLineRunner seedAdmin(AppUserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (users.findByUsername("admin").isEmpty()) {
                AppUser admin = new AppUser();
                admin.setName("Library Admin");
                admin.setUsername("admin");
                admin.setEmail("admin@library.local");
                admin.setPassword(encoder.encode("Admin@12345"));
                admin.setRole(Role.ADMIN);
                users.save(admin);
            }
        };
    }
}
