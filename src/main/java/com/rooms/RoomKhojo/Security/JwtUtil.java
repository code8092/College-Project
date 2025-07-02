package com.rooms.RoomKhojo.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Keep your secret key private and secure
    private static final String SECRET_KEY_STRING = "genie#123nsoidjsodi5e8w52sdsdsdhusheihiheweihenkisizsseass";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour in milliseconds

    private final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    // ✅ Generate a new token with claims
    public String generateToken(String email, String role, long id) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // ✅ Validate JWT
    public boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("❌ Token expired: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("❌ Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("❌ Malformed token: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ JWT error: " + e.getMessage());
        }
        return false;
    }

    // ✅ Extract claims safely
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        Object id = claims.get("id");
        return (id instanceof Integer) ? ((Integer) id).longValue() : (Long) id;
    }

    public String getEmailFromToken(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // Optional: if you want separate owner/customer ID getters
    public Long getOwnerIdFromToken(String token) {
        return getIdFromToken(token);
    }

    public Long getCustomerIdFromToken(String token) {
        return getIdFromToken(token);
    }
}
