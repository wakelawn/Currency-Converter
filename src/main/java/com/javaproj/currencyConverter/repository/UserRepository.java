package com.javaproj.currencyConverter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaproj.currencyConverter.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByApiKey(String apiKey);
}