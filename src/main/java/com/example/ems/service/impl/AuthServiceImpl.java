package com.example.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.core.Authentication;

import com.example.ems.dto.LoginRequest;
import com.example.ems.dto.SignupRequest;
import com.example.ems.entity.Role;
import com.example.ems.entity.User;
import com.example.ems.repository.UserRepository;
import com.example.ems.security.JwtUtil;
import com.example.ems.service.AuthService;

@Service
// @CrossOrigin(origins = "http://localhost:8080")
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
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
        u.setPassword(passwordEncoder.encode(r.getPassword()));
        u.setRole(Role.valueOf(r.getRole().toUpperCase()));
        userRepository.save(u);
    }

    @Override
    public String login(LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUserName(), req.getPassword()));
        UserDetails ud = (UserDetails) auth.getPrincipal();
        return jwtUtil.generateToken(ud);
    }
}
