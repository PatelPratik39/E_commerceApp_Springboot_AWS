package com.ecommerce.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf ->
                        csrf.ignoringRequestMatchers("/h2-console/**")
                )
                .headers(headers ->
                        headers.frameOptions(frameOptions ->
                                frameOptions.sameOrigin()
                        )
                );

        return http.build();
    }
}