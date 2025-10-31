package com.example.petstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

// Imports for external image serving
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;
import java.nio.file.Paths;


@Configuration
// Implements WebMvcConfigurer to serve external files
public class SecurityConfig implements WebMvcConfigurer {

    /**
     * This method configures the "live" resource folder for file uploads.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This is the external folder path (e.g., C:/Users/YourName/petstore-uploads/)
        String uploadDir = System.getProperty("user.home") + "/petstore-uploads/";
        Path uploadPath = Paths.get(uploadDir);

        // This maps the URL path "/uploads/**" to the physical directory
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath.toAbsolutePath() + "/");
    }

    /**
     * This method configures Spring Security (authentication, authorization, login, etc.)
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // --- THIS IS THE FIX FOR THE 403 FORBIDDEN ERROR ---
                .csrf(csrf -> csrf.disable()) // Disables CSRF protection
                // ---------------------------------------------------

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/domestic", "/exotic", "/category/**",
                                "/toys", "/food", "/cart", "/cart/**", "/checkout",
                                "/css/**", "/webjars/**", "/images/**",
                                "/uploads/**" // Allows viewing of uploaded images
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

    /**
     * This method creates the in-memory admin user.
     */
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