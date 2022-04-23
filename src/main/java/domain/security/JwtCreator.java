package domain.security;

import constants.ApplicationProperties;
import domain.persistence.entities.User;
import domain.security.contracts.SessionCreator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtCreator implements SessionCreator {

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


    public ResponseCookie generateResponseCookie(User user) {
        var jwt = this.generate(user);
        return ResponseCookie.from(this.cookieName, jwt)
                             .path(this.cookiePath)
                             .maxAge(this.cookieExpirationSeconds)
                             .httpOnly(true)
                             .build();
    }

    private String generate(User user) {
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiration = issuedAt.plus(this.jwtExpirationMilliseconds, ChronoUnit.MILLIS);

        return Jwts.builder()
                   .setId(Long.toString(user.getId()))
                   .setSubject(user.getEmail())
                   .setIssuedAt(Date.from(issuedAt.toInstant(ZoneOffset.UTC)))
                   .setExpiration(Date.from(expiration.toInstant(ZoneOffset.UTC)))
                   .signWith(SignatureAlgorithm.HS512, this.jwtSecret)
                   .compact();


    }
}
