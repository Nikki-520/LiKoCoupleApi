package com.liko.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "LiKoJwtSecretKeyForTokenSigning!@#";
    private static final long EXPIRE = 7 * 24 * 60 * 60 * 1000L;

    public static String generate(Long userId, Long coupleId, String role) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("coupleId", coupleId)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRE))
                .signWith(key)
                .compact();
    }

    public static Claims parse(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
