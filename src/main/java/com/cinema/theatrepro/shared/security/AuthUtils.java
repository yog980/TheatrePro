package com.cinema.theatrepro.shared.security;


import com.cinema.theatrepro.User.model.User;
import com.cinema.theatrepro.shared.exception.UnauthorizedException;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {
    public static User getCurrentUser() {
        try {
            UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userPrinciple.getUsers();
        } catch (Exception e) {
            throw new UnauthorizedException("Please login and continue ...");
        }
    }
}
