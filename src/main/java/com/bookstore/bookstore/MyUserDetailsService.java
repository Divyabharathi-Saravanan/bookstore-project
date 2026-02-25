package com.bookstore.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("Checking login for: " + username); // Look for this in your terminal!
    
    com.bookstore.bookstore.User myUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found in H2"));

    return org.springframework.security.core.userdetails.User
            .withUsername(myUser.getUsername())
            .password("{noop}" + myUser.getPassword())
            .roles("USER")
            .build();
}
}