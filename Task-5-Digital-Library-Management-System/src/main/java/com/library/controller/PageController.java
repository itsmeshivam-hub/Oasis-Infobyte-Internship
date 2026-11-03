package com.library.controller;

import com.library.model.*;
import com.library.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {
    private final AppUserRepository users; private final BookRepository books; private final PasswordEncoder encoder;
    public PageController(AppUserRepository users, BookRepository books, PasswordEncoder encoder){this.users=users;this.books=books;this.encoder=encoder;}
    @GetMapping("/") public String index(){return "index";}
    @GetMapping("/login") public String login(){return "login";}
    @GetMapping("/home") public String home(Authentication auth){
        AppUser u=users.findByUsername(auth.getName()).orElseThrow();
        return u.getRole()==Role.ADMIN ? "redirect:/admin" : "redirect:/user/books";
    }
    @GetMapping("/register") public String register(Model model){model.addAttribute("user",new AppUser());return "register";}
    @PostMapping("/register") public String register(@ModelAttribute AppUser user, RedirectAttributes ra){
        if(users.findByUsername(user.getUsername()).isPresent() || users.findByEmail(user.getEmail()).isPresent()){
            ra.addFlashAttribute("error","Username or email already exists."); return "redirect:/register";
        }
        user.setRole(Role.USER); user.setPassword(encoder.encode(user.getPassword())); users.save(user);
        ra.addFlashAttribute("message","Registration successful. Please log in."); return "redirect:/login";
    }
}
