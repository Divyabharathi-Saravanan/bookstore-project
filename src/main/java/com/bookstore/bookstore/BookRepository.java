package com.bookstore.bookstore;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    // This allows you to search for books by title (ignoring upper/lower case)
    List<Book> findByTitleContainingIgnoreCase(String title);
}