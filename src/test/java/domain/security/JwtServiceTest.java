package domain.security;

import domain.security.users.WordleUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        this.jwtService = new JwtService();

        this.jwtService.jwtExpirationMilliseconds = 10000L;
        this.jwtService.jwtSecret = "secret";
        this.jwtService.cookieExpirationSeconds = 1000L;
        this.jwtService.cookieName = "cookie";
        this.jwtService.cookiePath = "path";
        this.jwtService.cookieSameSite = "none";
    }

    @Test
    void generateResponseCookie() {
        final var wordleUser = new WordleUser("a name", "@1String", "some@email.com", UUID.randomUUID());

        final var responseCookie = this.jwtService.generateResponseCookie(wordleUser);

        assertNotNull(responseCookie.getValue());
        assertEquals(this.jwtService.cookieExpirationSeconds, responseCookie.getMaxAge().getSeconds());
        assertEquals(this.jwtService.cookieName, responseCookie.getName());
        assertEquals(this.jwtService.cookiePath, responseCookie.getPath());
        assertEquals(this.jwtService.cookieSameSite, responseCookie.getSameSite());
        assertTrue(responseCookie.isSecure());
        assertFalse(responseCookie.isHttpOnly());
    }

    @Test
    void extractClaims() {
        final var wordleUser = new WordleUser("a name", "@1String", "some@email.com", UUID.randomUUID());

        final var responseCookie = this.jwtService.generateResponseCookie(wordleUser);

        final var claims = this.jwtService.extractClaims(responseCookie.getValue());
        final var jtiClaimValue = claims.get("jti");
        final var subClaimValue = claims.get("sub");

        assertEquals(wordleUser.getId().toString(), jtiClaimValue);
        assertEquals(wordleUser.getEmail(), subClaimValue);
    }
}