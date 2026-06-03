package com.parksafe.api.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.parksafe.api.entity.User;

import io.jsonwebtoken.Jwts;

@Component
public class JwtService {

  private String secret;

  private long expiration;

  public String generateToken(User user) {
    return Jwts.builder()
        .subject(user.getUsername())
        .claim("role", user.getRole().name())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSigningKey())
        .compact();

  }

}
