package com.bookstore.bookstore;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // IMPORTANT: Add this import

public interface UserRepository extends JpaRepository<User, Long> {
    
    // This line is MISSING in your code. Add it now:
    Optional<User> findByUsername(String username);
}