package com.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Book {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank private String title;
    @NotBlank private String author;
    @Column(nullable=false, unique=true) @NotBlank private String isbn;
    @NotBlank private String category;
    @Min(0) private int quantity;
    @Min(0) private int availableQuantity;

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getTitle(){return title;} public void setTitle(String title){this.title=title;}
    public String getAuthor(){return author;} public void setAuthor(String author){this.author=author;}
    public String getIsbn(){return isbn;} public void setIsbn(String isbn){this.isbn=isbn;}
    public String getCategory(){return category;} public void setCategory(String category){this.category=category;}
    public int getQuantity(){return quantity;} public void setQuantity(int quantity){this.quantity=quantity;}
    public int getAvailableQuantity(){return availableQuantity;} public void setAvailableQuantity(int availableQuantity){this.availableQuantity=availableQuantity;}
}
