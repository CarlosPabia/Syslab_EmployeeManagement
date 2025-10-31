package com.example.petstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // not for production; simplifies form posts
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/domestic", "/exotic", "/category/**",
                                "/toys", "/food", "/cart", "/cart/**", "/checkout",
                                "/css/**", "/webjars/**", "/images/**"
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
