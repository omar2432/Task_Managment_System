package com.hitachi.taskService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/tasks/**").permitAll() // Allow public access to your endpoint
                        .anyRequest().authenticated() // Protect other endpoints
                );
//                .oauth2Login().disable(); // Disable the default OAuth2 login flow

        return http.build();
    }
}
