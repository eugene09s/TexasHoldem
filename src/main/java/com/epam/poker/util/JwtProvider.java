package com.epam.poker.util;

import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.epam.poker.controller.command.constant.Attribute;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class JwtProvider {
    private static final JwtProvider instance = new JwtProvider() ;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long TOKEN_LIFETIME = 15L;

    private JwtProvider() {
    }

    public static JwtProvider getInstance() {
        return instance;
    }

    public String generateToken(Map<String, String> claims) {
        Date exp = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(TOKEN_LIFETIME));
        Date now = new Date();
        String id = UUID.randomUUID().toString().replace("-", "");
        return Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(exp)
                .setIssuer("Eugene")
                .setSubject("Shadura")
                .setClaims(claims)
                .signWith(this.secretKey)
                .compact();
    }

    public String findValueByAttributeFromClaimsToken(Jws<Claims> claims, Attribute attribute) {
        return String.valueOf(claims.getBody().get(attribute));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            LOGGER.info("Token expired: " + expEx);
        } catch (UnsupportedJwtException unsEx) {
            LOGGER.info("Unsupported jwt: " + unsEx);
        } catch (MalformedJwtException mjEx) {
            LOGGER.info("Malformed jwt: " + mjEx);
        } catch (SignatureGenerationException sEx) {
            LOGGER.info("Invalid signature: " + sEx);
        } catch (Exception e) {
            LOGGER.info("invalid token: " + e);
        }
        return false;
    }

    public Jws<Claims> getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }
}
