package com.greenacademy.tiketinaja.utils;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import java.util.Date;
import com.greenacademy.tiketinaja.config.JwtConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {

    private JwtConfig jwtConfig;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public SecretKey generatedKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getJwtSecret().getBytes());
    }

    public String generatedToken(String email) {
        long expirationTime = 1000 * 60 * 60 * 24;
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis() + expirationTime);
        return Jwts.builder()
                .expiration(expiryDate)
                .issuedAt(now)
                .subject(email)
                .signWith(generatedKey())
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(generatedKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().verifyWith(generatedKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
