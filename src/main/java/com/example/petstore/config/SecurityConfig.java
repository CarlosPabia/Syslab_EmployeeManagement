package com.example.petstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${app.admin.username}")
    private String adminUser;
    @Value("${app.admin.password}")
    private String adminPass;

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withUsername(adminUser)
                .password(adminPass) // {noop} in properties
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/css/**", "/images/**", "/js/**",
                                 "/domestic/**", "/exotic/**", "/toys", "/food", "/cart/**", "/checkout", "/purchase").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
            )
            .formLogin(login -> login
                .loginPage("/admin/login").permitAll()
                .defaultSuccessUrl("/admin/dashboard", true)
            )
            .logout(logout -> logout.logoutUrl("/admin/logout").logoutSuccessUrl("/"))
            .csrf(csrf -> csrf.disable()); // Keep simple for demo; enable in prod
        return http.build();
    }
}
