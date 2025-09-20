package com.example.ems.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import com.example.ems.dto.LoginRequest;
import com.example.ems.dto.SignupRequest;
import com.example.ems.entity.Role;
import com.example.ems.entity.User;
import com.example.ems.repository.UserRepository;
import com.example.ems.security.JwtUtil;
import com.example.ems.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void regiseter(SignupRequest r) {
        if (userRepository.existsByuserName(r.getUserName())) {
            throw new RuntimeException("userName already taken");
        }

        if (userRepository.existsByEmail(r.getEmail())) {
            throw new RuntimeException("Email already taken");
        }

        User u = new User();
        u.setUserName(r.getUserName());
        u.setEmail(r.getEmail());
        u.setPassword(passwordEncoder.encode(r.getPassword())); // hashed password
        u.setRole(Role.valueOf(r.getRole().toUpperCase()));
        userRepository.save(u);
    }

    @Override
    public String login(LoginRequest req) {
        Optional<User> u = userRepository.findByUserName(req.getUserName());
        if (u.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        if (!passwordEncoder.matches(req.getPassword(), u.get().getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateToken(u.get().getUserName());
    }
}
