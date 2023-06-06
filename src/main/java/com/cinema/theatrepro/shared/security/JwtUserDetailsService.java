package com.cinema.theatrepro.shared.security;

import com.cinema.theatrepro.User.model.User;
import com.cinema.theatrepro.User.service.UserService;
import com.cinema.theatrepro.shared.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService usersService;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User users = usersService.findUserByUserName(username).orElseThrow(() ->
                new ClientException("User not found with username = "+username)
            );
            UserPrinciple userPrinciple = UserPrinciple.create(users);
            return userPrinciple;
    }
}
