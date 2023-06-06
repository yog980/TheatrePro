package com.cinema.theatrepro.shared.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtResponse {
    private int code;
    private boolean status;
    private String message;
    private String jwtToken;
}
