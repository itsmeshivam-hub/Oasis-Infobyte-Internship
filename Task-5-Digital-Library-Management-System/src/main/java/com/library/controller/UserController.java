package com.library.controller;

import com.library.model.*;
import com.library.repository.*;
import com.library.service.LibraryService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final BookRepository books; private final AppUserRepository users; private final LoanRepository loans;
    private final FineRepository fines; private final BookReservationRepository reservations; private final LibraryQueryRepository queries;
    private final LibraryService service;
    public UserController(BookRepository b, AppUserRepository u, LoanRepository l, FineRepository f,
        BookReservationRepository r, LibraryQueryRepository q, LibraryService s){books=b;users=u;loans=l;fines=f;reservations=r;queries=q;service=s;}

    @GetMapping("/books") public String books(@RequestParam(required=false) String q,@RequestParam(required=false) String category,Model m){
        List<Book> list;
        if(q!=null && !q.isBlank()) list=books.search(q);
        else if(category!=null && !category.isBlank()) list=books.findByCategoryIgnoreCase(category);
        else list=books.findAll();
        m.addAttribute("books",list); m.addAttribute("categories",books.findAll().stream().map(Book::getCategory).distinct().toList());
        return "user-books";
    }
    @PostMapping("/issue/{id}") public String issue(@PathVariable Long id,Authentication a,RedirectAttributes ra){
        try {service.issueBook(id,a.getName());ra.addFlashAttribute("message","Book issued. Due date is 14 days from today.");}
        catch(RuntimeException e){ra.addFlashAttribute("error",e.getMessage());}
        return "redirect:/user/books";
    }
    @GetMapping("/loans") public String loans(Authentication a,Model m){
        AppUser u=users.findByUsername(a.getName()).orElseThrow(); m.addAttribute("loans",loans.findByUser(u)); return "user-loans";
    }
    @PostMapping("/return/{id}") public String returnBook(@PathVariable Long id,Authentication a,RedirectAttributes ra){
        try {var fine=service.returnBook(id,a.getName());ra.addFlashAttribute("message","Book returned. Fine generated: ₹"+fine);}
        catch(RuntimeException e){ra.addFlashAttribute("error",e.getMessage());}
        return "redirect:/user/loans";
    }
    @PostMapping("/reserve/{id}") public String reserve(@PathVariable Long id,Authentication a,RedirectAttributes ra){
        try {service.reserveBook(id,a.getName());ra.addFlashAttribute("message","Reservation added to the waiting list.");}
        catch(RuntimeException e){ra.addFlashAttribute("error",e.getMessage());}
        return "redirect:/user/books";
    }
    @GetMapping("/fines") public String fines(Authentication a,Model m){AppUser u=users.findByUsername(a.getName()).orElseThrow();m.addAttribute("fines",fines.findByUser(u));return "user-fines";}
    @GetMapping("/contact") public String contact(){return "contact";}
    @PostMapping("/contact") public String contact(@RequestParam String message,Authentication a,RedirectAttributes ra){
        if(message==null || message.isBlank()){ra.addFlashAttribute("error","Message cannot be empty.");return "redirect:/user/contact";}
        LibraryQuery q=new LibraryQuery();q.setUser(users.findByUsername(a.getName()).orElseThrow());q.setMessage(message);queries.save(q);
        ra.addFlashAttribute("message","Your query has been sent to the admin.");return "redirect:/user/contact";
    }
}
