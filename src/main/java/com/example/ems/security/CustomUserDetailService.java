package com.example.ems.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.ems.entity.User;
import com.example.ems.repository.UserRepository;

public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found" + username));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + u.getRole().name());
        return new org.springframework.security.core.userdetails.User(u.getUserName(), u.getPassword(),
                Collections.singleton(authority));
    }
}
