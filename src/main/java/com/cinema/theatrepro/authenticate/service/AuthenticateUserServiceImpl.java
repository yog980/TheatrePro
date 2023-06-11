package com.cinema.theatrepro.authenticate.service;


import com.cinema.theatrepro.User.service.UserService;
import com.cinema.theatrepro.shared.security.JwtRequest;
import com.cinema.theatrepro.shared.security.JwtResponse;
import com.cinema.theatrepro.shared.security.JwtTokenUtil;
import com.cinema.theatrepro.shared.security.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class AuthenticateUserServiceImpl implements AuthenticateUserService{
    private JwtTokenUtil jwtTokenUtil;
    private final UserService usersService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUserDetailsService userDetailsService;


    public AuthenticateUserServiceImpl(JwtUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil,
                                       UserService usersService,
                                       PasswordEncoder passwordEncoder,
                                       AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JwtResponse AuthenticateUser(JwtRequest jwtRequest) {
        log.info(passwordEncoder.encode(jwtRequest.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        log.info("User Details :: {}",userDetails);
        if(!passwordEncoder.matches(jwtRequest.getPassword(),userDetails.getPassword())) {
            return JwtResponse.builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .status(Boolean.FALSE)
                    .message("Invalid Credentials")
                    .build();
        }
        String token = jwtTokenUtil.generateToken(userDetails);
        return JwtResponse.builder()
                .code(HttpStatus.ACCEPTED.value())
                .status(Boolean.TRUE)
                .message("Successfully authenticated")
                .jwtToken(token)
                .build();
        }

        private boolean isPasswordValid(String rawPassword,String encodedPassword) {
            return passwordEncoder.matches(rawPassword,encodedPassword);
        }
}
