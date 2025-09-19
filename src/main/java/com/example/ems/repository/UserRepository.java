package com.example.ems.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ems.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}