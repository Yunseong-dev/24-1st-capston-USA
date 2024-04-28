package com.capstone.usa.security;

import com.capstone.usa.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private String jwtSecret;
    private Key jwtKey;
    private long jwtExpirationInMs;

    public JwtUtil(
            @Value("${app.jwtSecret}")
            String jwtSecret,
            @Value("${app.accessTokenExpirationInMs}")
            long accessTokenExpirationInMs,
            @Value("${app.refreshTokenExpirationInMs}")
            long refreshTokenExpirationInMs
    )
    {
        this.jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        this.jwtKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.jwtExpirationInMs = accessTokenExpirationInMs;
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        Claims claims = Jwts.claims().setSubject(user.getName());
        claims.setIssuedAt(new Date());
        claims.setExpiration(expiryDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(jwtKey)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }
}
