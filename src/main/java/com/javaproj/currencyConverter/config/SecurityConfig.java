package com.javaproj.currencyConverter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.javaproj.currencyConverter.service.UserService;

@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers("/h2-console/**").permitAll()
            	    .requestMatchers("/swagger-ui/**").permitAll()
            	    .requestMatchers("/v3/api-docs/**").permitAll()
            	    .anyRequest().authenticated()
            	)

            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )

            .addFilterBefore(
                new ApiKeyAuthenticationFilter(userService),
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}