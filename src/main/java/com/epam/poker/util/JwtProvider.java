package com.epam.poker.util;

import com.auth0.jwt.exceptions.SignatureGenerationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JwtProvider {
    private static final JwtProvider instance = new JwtProvider() ;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int TOKEN_LIFETIME = 15;
//    @Value("${jwt.secret}")
    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private JwtProvider() {
    }

    public static JwtProvider getInstance() {
        return instance;
    }

    public String generateToken(Map<String, String> claims) {
        Date date = Date.from(LocalDate.now().plusDays(TOKEN_LIFETIME).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setIssuer("Stormpath")
                .setSubject("msilverman")
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(date)
                .signWith(this.secretKey)
//                .setId(UUID.randomUUID()
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            LOGGER.info("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            LOGGER.info("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            LOGGER.info("Malformed jwt");
        } catch (SignatureGenerationException sEx) {
            LOGGER.info("Invalid signature");
        } catch (Exception e) {
            LOGGER.info("invalid token");
        }
        return false;
    }

    public Jws<Claims> getClaimsFromToken(String token) {
//        Claims jws;
//        jws = Jwts.parserBuilder()  // (1)
//                .setSigningKey(key)         // (2)
//                .build()                    // (3)
//                .parseClaimsJws(token).getBody();
        Jws<Claims> jws;
        jws = Jwts.parserBuilder()  // (1)
                .setSigningKey(secretKey)         // (2)
                .build()                    // (3)
                .parseClaimsJws(token); // (4)
        return jws;
    }
}
