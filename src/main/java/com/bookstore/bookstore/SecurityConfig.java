package com.bookstore.bookstore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Allows H2 and form submissions
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/css/**", "/js/**", "/image/**").permitAll()
                .requestMatchers("/inventory-view", "/h2-console/**").permitAll()
                .requestMatchers("/edit-book/**", "/add-to-cart/**", "/delete-book/**", "/save-book/**", "/add-book").permitAll()
                .requestMatchers("/cart", "/order-success").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
    .loginPage("/login")
    .successHandler((request, response, authentication) -> {
        // This is a "hard" redirect that ignores typical security loops
        response.sendRedirect("/inventory-view");
    })
    .permitAll()
)
            .logout(logout -> logout.permitAll())
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}