package com.cinema.theatrepro.User.model;

import com.cinema.theatrepro.shared.abstracts.AbstractEntity;
import com.cinema.theatrepro.shared.enums.RoleName;
import com.cinema.theatrepro.shared.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends AbstractEntity {
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String contact;
    private RoleName roleName;
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_id",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();
}
