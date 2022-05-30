package controllers;

import constants.SuppressWarningsConstants;
import domain.requests.posts.RequestPostLogin;
import domain.security.WordleAuthenticationManager;
import domain.security.contracts.SessionCreator;
import domain.security.users.WordleUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
@SuppressWarnings(SuppressWarningsConstants.ALL)
public class LoginControllerTest {

    @Mock
    private WordleAuthenticationManager wordleAuthenticationManager;

    @Mock
    private SessionCreator sessionCreator;

    @InjectMocks
    private LoginsController loginsController;

    @Test
    void post() {
        final var userId = UUID.randomUUID();

        RequestPostLogin requestPostLogin = new RequestPostLogin();
        requestPostLogin.setEmail("someEmail@email.com");
        requestPostLogin.setPassword("somePassword");

        final var wordleUser = new WordleUser("someName", "pass", "someEmail@email.com", userId);
        final var responseCookie = ResponseCookie.from("someName", "valueCookie").build();

        Mockito.when(this.wordleAuthenticationManager.authenticate(requestPostLogin.getEmail(),requestPostLogin.getPassword())).thenReturn(wordleUser);
        Mockito.when(this.sessionCreator.generateResponseCookie(wordleUser)).thenReturn(responseCookie);

        var responseEntity = this.loginsController.post(requestPostLogin);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(wordleUser.getUsername(), responseEntity.getBody().name());
        assertEquals(responseCookie.toString(), responseEntity.getHeaders().get("Set-Cookie").get(0));

        Mockito.verify(this.wordleAuthenticationManager, Mockito.times(1)).authenticate(requestPostLogin.getEmail(), requestPostLogin.getPassword());
        Mockito.verify(this.sessionCreator, Mockito.times(1)).generateResponseCookie(wordleUser);
    }
}
