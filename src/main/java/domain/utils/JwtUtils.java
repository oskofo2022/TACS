package domain.utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

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
        Date issuedAt = new Date();
        Date expiration = (Date) issuedAt.clone();
        expiration.setTime(issuedAt.getTime()+Long.parseLong(jwtExpirationMs));

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

}
