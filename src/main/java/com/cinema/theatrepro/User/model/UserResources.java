package com.cinema.theatrepro.User.model;

import com.cinema.theatrepro.shared.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResources {
    private Long userId;
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String contact;
    private String role;
    private Status status;
}
