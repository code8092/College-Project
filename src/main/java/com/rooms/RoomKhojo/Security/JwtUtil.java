package com.rooms.RoomKhojo.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY_STRING="genie#123nsoidjsodi5e8w52sdsdsdhusheihiheweihenkisizsseass";
    private final Key SECRET_KEY= Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    public String generateToken(String email, String role){
        String token= Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
        System.out.println("Generated token");
        return token;
    }

    public boolean validate(String token){
        try{
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token) ;
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token Expired "+e.getMessage());
            return false;
        }
        catch (SignatureException e){
            System.out.println("Invalid JWT Signature "+e.getMessage());
            return false;
        }
        catch (Exception e){
            System.out.println("JWT exception "+e.getMessage());
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getRoleFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

}