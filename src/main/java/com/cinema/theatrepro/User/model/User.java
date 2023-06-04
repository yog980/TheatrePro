package com.cinema.theatrepro.User.model;

import com.cinema.theatrepro.shared.abstracts.AbstractEntity;
import com.cinema.theatrepro.shared.enums.Role;
import com.cinema.theatrepro.shared.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class User extends AbstractEntity {
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String contact;
    private Role role;
    private Status status;
}
