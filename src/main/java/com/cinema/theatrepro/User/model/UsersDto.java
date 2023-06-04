package com.cinema.theatrepro.User.model;

import com.cinema.theatrepro.shared.enums.Role;
import com.cinema.theatrepro.shared.enums.Status;
import lombok.Data;

@Data
public class UsersDto {
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String contact;
    private String role;
    private Status status;
}
