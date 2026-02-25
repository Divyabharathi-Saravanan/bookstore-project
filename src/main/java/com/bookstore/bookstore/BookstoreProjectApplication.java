package com.bookstore.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.ArrayList;

@SpringBootApplication
@Controller
public class BookstoreProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreProjectApplication.class, args);
    }

    @Autowired
    private BookRepository repo;

    @Autowired
    private UserRepository userRepo; // Moved to top for consistency

    private List<Book> cart = new ArrayList<>();

    @Bean
    public CommandLineRunner loadData(BookRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Book("The Great Gatsby", "F. Scott Fitzgerald", 450.0));
                repo.save(new Book("1984", "George Orwell", 275.0));
                repo.save(new Book("The Hobbit", "J.R.R. Tolkien", 290.0));
                repo.save(new Book("To Kill a Mockingbird", "Harper Lee", 1400.0));
                repo.save(new Book("Brave New World", "Aldous Huxley", 199.0));
                repo.save(new Book("Java Programming", "Technical Press", 450.0));
                repo.save(new Book("The Alchemist", "Paulo Coelho", 1899.0));
                repo.save(new Book("Little Women", "Louisa May Alcott", 999.0));
                repo.save(new Book("Frankenstein", "Mary Shelley", 350.0));
                repo.save(new Book("Dracula", "Bram Stoker", 320.0));
                System.out.println(">>> 10 Books inserted into database!");
            }
        };
    }

    @GetMapping("/login")
    public String login() {
        return "login"; 
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User()); 
        return "register"; 
    }

    @PostMapping("/register")
public String registerUser(@ModelAttribute User user) {
    // This command sends the data to your H2 database table
    userRepo.save(user); 
    System.out.println("User stored in database: " + user.getUsername());
    return "redirect:/login"; 
}

    @GetMapping("/inventory-view")
    public String showInventory(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Book> books;
        if (keyword != null && !keyword.isEmpty()) {
            books = repo.findByTitleContainingIgnoreCase(keyword);
        } else {
            books = repo.findAll();
        }
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        return "inventory"; 
    }

    @GetMapping("/add-book")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/save-book")
    public String saveBook(@ModelAttribute Book book) {
        repo.save(book);
        return "redirect:/inventory-view";
    }

    @GetMapping("/edit-book/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Book book = repo.findById(id).orElseThrow();
        model.addAttribute("book", book);
        return "edit-book";
    }

    @GetMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/inventory-view";
    }

    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id) {
        repo.findById(id).ifPresent(book -> cart.add(book));
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cart);
        double total = cart.stream().mapToDouble(Book::getPrice).sum();
        model.addAttribute("total", total);
        return "cart";
    }

    @GetMapping("/order-success")
    public String orderSuccess() {
        cart.clear();
        return "success"; 
    }
}