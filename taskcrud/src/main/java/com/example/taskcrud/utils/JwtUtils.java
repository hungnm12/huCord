package com.example.taskcrud.utils;

import com.example.taskcrud.Repository.IAppUserRepository;
import com.example.taskcrud.entity.appuser.AppUser;
import com.example.taskcrud.security.config.JwtService;

public class JwtUtils {
    public static AppUser getUserFromToken(JwtService jwtService, IAppUserRepository userRepository, String token) {
        String username = jwtService.extractUserName(token);
        return userRepository.findByEmail(username);
    }
}

