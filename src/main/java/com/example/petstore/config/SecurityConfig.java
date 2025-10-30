package com.example.petstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/", "/domestic/**", "/exotic/**", "/toys", "/food",
                               "/cart/**", "/checkout/**", "/css/**", "/webjars/**").permitAll()
              .requestMatchers("/admin/**").authenticated()
          )
          .formLogin(login -> login
              .loginPage("/admin/login")
              .defaultSuccessUrl("/admin", true)
              .permitAll())
          .logout(logout -> logout
              .logoutUrl("/admin/logout")
              .logoutSuccessUrl("/"))
          .csrf(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager uds() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}
