package com.javaproj.currencyConverter.config;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.javaproj.currencyConverter.model.User;
import com.javaproj.currencyConverter.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {

        if (userRepository.count() == 0) {

            String apiKey = UUID.randomUUID().toString();

            User user = new User("testuser", apiKey);

            userRepository.save(user);

            System.out.println("=====================");
            System.out.println("TEST USER CREATED");
            System.out.println("Username: testuser");
            System.out.println("API Key: " + apiKey);
            System.out.println("======================");
        }
    }
}