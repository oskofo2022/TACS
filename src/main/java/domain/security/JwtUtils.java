package domain.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${tacs.wordle.jwtSecret}")
    public String jwtSecret;

    @Value("${tacs.wordle.jwtExpirationMs}")
    public String jwtExpirationMs;

    @Value("${tacs.wordle.jwtCookieName}")
    public String jwtCookieName;

    public ResponseCookie generateJwtCookie(String username) {
        String jwt = generateTokenFromUsername(username);
        return ResponseCookie.from(jwtCookieName, jwt)
                .path("/")
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .build();
    }

    public String generateTokenFromUsername(String username) {
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiration = issuedAt.plus(Long.parseLong(jwtExpirationMs), ChronoUnit.MILLIS);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(issuedAt.toInstant(ZoneOffset.UTC)))
                .setExpiration(Date.from(expiration.toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

}
