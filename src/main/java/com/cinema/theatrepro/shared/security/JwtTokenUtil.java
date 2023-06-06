package com.cinema.theatrepro.shared.security;

import com.cinema.theatrepro.shared.PropertyValue;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenUtil implements Serializable {
    @Value(value = PropertyValue.JWT_EXPIRATION_IN_MS)
    private String jwtTokenValidity;
    @Value(value = PropertyValue.JWT_SECRET)
    private String secretKey;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(jwtTokenValidity)))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
        return String.valueOf(getAllClaimsFromToken(token).getSubject());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token,Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token,UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        log.info("username :: {} and :: {}",username,userDetails);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}