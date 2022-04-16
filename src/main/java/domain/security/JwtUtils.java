package domain.security;

import domain.repositories.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${tacs.wordle.jwtSecret}")
    public String jwtSecret;

    @Value("${tacs.wordle.jwtExpirationMs}")
    public String jwtExpirationMs;

    @Value("${tacs.wordle.jwtCookieName}")
    public String jwtCookieName;

    public LocalDate extractExpiration(String token){
        return LocalDate.from(extractClaim(token, Claims::getExpiration).toInstant());
    }

    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public ResponseCookie generateJwtCookie(String username) {
        String jwt = generateTokenFromUsername(username);
        return ResponseCookie.from(jwtCookieName, jwt)
                .path("/")
                .maxAge(2 * 60 * 60)
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
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .compact();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).isBefore(LocalDate.now());
    }

    public Boolean validateToken(String token, User user){
        final String email = extractEmail(token);
        return (email.equals(user.getEmail()));
    }

//    @Bean
//    public EntityManagerFactory entityManagerFactory(){
//        return Persistence.createEntityManagerFactory("wordleDB")
//    }

}
