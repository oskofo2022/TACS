package controllers;

import constants.UriConstants;
import constants.MediaTypeConstants;
import domain.errors.runtime.LoginCredentialsRuntimeException;
import domain.persistence.entities.User;
import domain.persistence.repositories.UserRepository;
import domain.requests.posts.RequestPostLogin;
import domain.responses.posts.ResponsePostUserLogin;
import domain.security.WordleAuthenticationManager;
import domain.security.contracts.SessionCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponsePostUserLogin> post(@Valid @RequestBody RequestPostLogin requestPostLogin) {

        final var wordleUser = this.wordleAuthenticationManager.authenticate(requestPostLogin.getEmail(), requestPostLogin.getPassword());

        final var responseCookie = this.sessionCreator.generateResponseCookie(wordleUser);
        final var ResponsePostUserLogin = new ResponsePostUserLogin(wordleUser.getUsername());

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                             .body(ResponsePostUserLogin);
    }
}
