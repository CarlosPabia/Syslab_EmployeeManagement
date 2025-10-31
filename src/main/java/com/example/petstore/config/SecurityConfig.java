package com.example.petstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

// --- CHANGE #1 ---
// Add these two new imports
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;
import java.nio.file.Path; // <-- THIS IS THE MISSING IMPORT// Add this import

@Configuration
// --- CHANGE #2 ---
// Implement the WebMvcConfigurer interface
public class SecurityConfig implements WebMvcConfigurer {

    // --- CHANGE #3 ---
    // Add this new method to configure the "live" resource folder
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This is the external folder path from Step 1
        String uploadDir = System.getProperty("user.home") + "/petstore-uploads/";
        Path uploadPath = Paths.get(uploadDir);

        // This maps the URL path "/uploads/**" to the physical directory
        // The "file:" prefix is essential for serving from the file system.
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath.toAbsolutePath() + "/");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // not for production; simplifies form posts
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/domestic", "/exotic", "/category/**",
                                "/toys", "/food", "/cart", "/cart/**", "/checkout",
                                "/css/**", "/webjars/**", "/images/**",
                                "/uploads/**" // <-- CHANGE #4: Also permit the new /uploads/ URL
                        ).permitAll()
                        .requestMatchers("/admin/login").permitAll()
                        .requestMatchers("/admin/**").authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/admin/login")           // GET login page
                        .loginProcessingUrl("/admin/login")  // POST from the form goes here
                        .defaultSuccessUrl("/admin", true)   // where to go after success
                        .failureUrl("/admin/login?error")    // on failure
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails admin = User
                .withUsername("admin")
                .password("{noop}admin123") // {noop} = plain text for demo
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}