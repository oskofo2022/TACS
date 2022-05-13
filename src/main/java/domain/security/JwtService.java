package domain.security;

import constants.ApplicationProperties;
import domain.security.contracts.SessionCreator;
import domain.security.users.WordleUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService implements SessionCreator {

    @Value(ApplicationProperties.Wordle.Jwt.Arguments.SECRET)
    public String jwtSecret;

    @Value(ApplicationProperties.Wordle.Jwt.Arguments.EXPIRATION)
    public Long jwtExpirationMilliseconds;

    @Value(ApplicationProperties.Wordle.Cookie.Arguments.NAME)
    public String cookieName;

    @Value(ApplicationProperties.Wordle.Cookie.Arguments.EXPIRATION)
    public Long cookieExpirationSeconds;

    @Value(ApplicationProperties.Wordle.Cookie.Arguments.PATH)
    public String cookiePath;

    @Value(ApplicationProperties.Wordle.Cookie.Arguments.SAME_SITE)
    public String cookieSameSite;

    public ResponseCookie generateResponseCookie(WordleUser wordleUser) {
        var jwt = this.generate(wordleUser);
        return ResponseCookie.from(this.cookieName, jwt)
                             .path(this.cookiePath)
                             .sameSite(this.cookieSameSite)
                             .secure(true)
                             .maxAge(this.cookieExpirationSeconds)
                             .build();
    }

    private String generate(WordleUser wordleUser) {
        return Jwts.builder()
                   .setId(Long.toString(wordleUser.getId()))
                   .setSubject(wordleUser.getEmail())
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + this.jwtExpirationMilliseconds))
                   .signWith(SignatureAlgorithm.HS512, this.jwtSecret)
                   .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                   .setSigningKey(this.jwtSecret)
                   .parseClaimsJws(token)
                   .getBody();
    }
}
