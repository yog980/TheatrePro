package com.cinema.theatrepro.shared.security;

import com.cinema.theatrepro.User.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserPrinciple implements UserDetails {
    private final User users;

    private Long id;

    private String name;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(Long id,
                         String name,
                         String username,
                         String email,
                         String password,
                         Collection<? extends GrantedAuthority> authorities,
                         User users) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.users = users;
    }

    public static UserPrinciple create(User users) {
//        List<GrantedAuthority> authorities = users.getRoleName().stream().map(role ->
//                new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
        UserPrinciple userPrinciple =  new UserPrinciple(
                users.getId(),
                users.getFullName(),
                users.getUsername(),
                users.getEmail(),
                users.getPassword(),
        null,
//                authorities,
                users
        );
        return userPrinciple;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
