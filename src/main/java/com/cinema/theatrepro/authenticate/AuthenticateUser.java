package com.cinema.theatrepro.authenticate;

import com.cinema.theatrepro.authenticate.service.AuthenticateUserService;
import com.cinema.theatrepro.shared.security.JwtRequest;
import com.cinema.theatrepro.shared.security.JwtResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authenticate")
public class AuthenticateUser {
    private AuthenticateUserService authenticateUserService;

    public AuthenticateUser(AuthenticateUserService authenticateUserService) {
        this.authenticateUserService = authenticateUserService;
    }

    @PostMapping("")
    public JwtResponse authenticateUser(@RequestBody JwtRequest jwtRequest) {
       return authenticateUserService.AuthenticateUser(jwtRequest);
    }
}
