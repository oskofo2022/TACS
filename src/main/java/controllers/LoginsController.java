package controllers;

import constants.UriConstants;
import constants.MediaTypeConstants;
import domain.requests.posts.RequestPostLogin;
import domain.responses.posts.ResponsePostUserLogin;
import domain.security.WordleAuthenticationManager;
import domain.security.contracts.SessionCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = UriConstants.Logins.URL)
public class LoginsController {

    private final SessionCreator sessionCreator;
    private final WordleAuthenticationManager wordleAuthenticationManager;

    @Autowired
    public LoginsController(SessionCreator sessionCreator, WordleAuthenticationManager wordleAuthenticationManager) {
        this.sessionCreator = sessionCreator;
        this.wordleAuthenticationManager = wordleAuthenticationManager;
    }

    @PostMapping(consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponsePostUserLogin> post(@Valid @RequestBody RequestPostLogin requestPostLogin) {

        final var user = this.wordleAuthenticationManager.authenticate(requestPostLogin.getEmail(), requestPostLogin.getPassword());

        final var responseCookie = this.sessionCreator.generateResponseCookie(user);
        final var ResponsePostUserLogin = new ResponsePostUserLogin(user.getUsername());

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .header("x-session", "session")
                             .body(ResponsePostUserLogin);
    }
}
