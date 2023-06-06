package com.cinema.theatrepro.authenticate.service;


import com.cinema.theatrepro.shared.security.JwtRequest;
import com.cinema.theatrepro.shared.security.JwtResponse;

public interface AuthenticateUserService {
    JwtResponse AuthenticateUser(JwtRequest jwtRequest);
}
