package com.javaproj.currencyConverter.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.javaproj.currencyConverter.model.User;
import com.javaproj.currencyConverter.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByApiKey(String apiKey) {
        return userRepository.findByApiKey(apiKey);
    }
}