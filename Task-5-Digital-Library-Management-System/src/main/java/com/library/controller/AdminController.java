package com.library.controller;

import com.library.model.*;
import com.library.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final BookRepository books; private final AppUserRepository users; private final LoanRepository loans;
    private final FineRepository fines; private final LibraryQueryRepository queries;
    public AdminController(BookRepository b,AppUserRepository u,LoanRepository l,FineRepository f,LibraryQueryRepository q){books=b;users=u;loans=l;fines=f;queries=q;}
    @GetMapping public String dashboard(Model m){
        m.addAttribute("bookCount",books.count());m.addAttribute("memberCount",users.findAll().stream().filter(u->u.getRole()==Role.USER).count());
        m.addAttribute("activeLoans",loans.findByReturnedFalse().size());m.addAttribute("unpaidFines",fines.findAll().stream().filter(f->!f.isPaid()).count());return "admin";
    }
    @GetMapping("/books") public String books(Model m){m.addAttribute("books",books.findAll());return "admin-books";}
    @GetMapping("/books/new") public String newBook(Model m){m.addAttribute("book",new Book());return "book-form";}
    @GetMapping("/books/edit/{id}") public String edit(@PathVariable Long id,Model m){m.addAttribute("book",books.findById(id).orElseThrow());return "book-form";}
    @PostMapping("/books/save") public String save(@ModelAttribute Book book,RedirectAttributes ra){
        if(book.getId()==null) book.setAvailableQuantity(book.getQuantity());
        else {Book old=books.findById(book.getId()).orElseThrow();int issued=old.getQuantity()-old.getAvailableQuantity();book.setAvailableQuantity(Math.max(0,book.getQuantity()-issued));}
        books.save(book);ra.addFlashAttribute("message","Book saved.");return "redirect:/admin/books";
    }
    @PostMapping("/books/delete/{id}") public String delete(@PathVariable Long id,RedirectAttributes ra){
        Book b=books.findById(id).orElseThrow();
        if(b.getAvailableQuantity()!=b.getQuantity()){ra.addFlashAttribute("error","Cannot delete a book with active issued copies.");}
        else {books.delete(b);ra.addFlashAttribute("message","Book deleted.");}
        return "redirect:/admin/books";
    }
    @GetMapping("/members") public String members(Model m){m.addAttribute("members",users.findAll().stream().filter(u->u.getRole()==Role.USER).toList());return "admin-members";}
    @GetMapping("/loans") public String loans(Model m){m.addAttribute("loans",loans.findAll());return "admin-loans";}
    @GetMapping("/fines") public String fines(Model m){m.addAttribute("fines",fines.findAll());return "admin-fines";}
    @PostMapping("/fines/pay/{id}") public String pay(@PathVariable Long id,RedirectAttributes ra){Fine f=fines.findById(id).orElseThrow();f.setPaid(true);fines.save(f);ra.addFlashAttribute("message","Fine marked as paid.");return "redirect:/admin/fines";}
    @GetMapping("/queries") public String queries(Model m){m.addAttribute("queries",queries.findAll());return "admin-queries";}
    @PostMapping("/queries/resolve/{id}") public String resolve(@PathVariable Long id){LibraryQuery q=queries.findById(id).orElseThrow();q.setResolved(true);queries.save(q);return "redirect:/admin/queries";}
}
