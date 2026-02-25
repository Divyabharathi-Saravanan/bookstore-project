package com.bookstore.bookstore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String author;
    private Double price;

    // --- ADDED CONSTRUCTORS (Fixes the "Book" errors) ---
    
    // 1. Default constructor (Required by JPA/Hibernate)
    public Book() {}

    // 2. Data constructor (Used to create your 10 books)
    public Book(String title, String author, Double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    // --- GETTERS AND SETTERS (Keep these for Thymeleaf) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}