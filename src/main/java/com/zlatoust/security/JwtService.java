package com.zlatoust.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.zlatoust.exceptions.InvalidJwtTokenException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    @Value("${app.security.jwt.secret-key}")
    private String secretKey;
    @Value("${app.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${app.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateToken(ClientDetails client) {
        return generateToken(new HashMap<>(), client);
    }

    public String generateToken(Map<String, Object> extraClaims,
                                ClientDetails client) {
        return buildToken(extraClaims, client, jwtExpiration);
    }

    public String generateRefreshToken(ClientDetails client) {
        return buildToken(new HashMap<>(), client, refreshExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims,
                              ClientDetails client, long expiration) {
        extraClaims.put("role", client.getUser().getRole());

        return Jwts.builder()
                .claims(extraClaims)
                .subject(client.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, ClientDetails client) {
        final String username = extractUsername(token);
        return (username.equals(client.getUsername())) && !isTokenExpired(token);
    }

    public boolean validateJwtToken(String authtoken) {
        try {
            Jwts.parser().verifyWith(getSignInKey()).build().parse(authtoken);
            return true;
        } catch (RuntimeException e) {
            throw new InvalidJwtTokenException("Invalid JWT Token : " + e.getMessage());
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
