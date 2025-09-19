package com.example.ems.service;

import com.example.ems.dto.LoginRequest;
import com.example.ems.dto.SignupRequest;

public interface AuthService {
    void regiseter(SignupRequest signupRequest);

    String login(LoginRequest loginRequest);
}