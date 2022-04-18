package controllers;

import constants.UriConstants;
import constants.MediaTypeConstants;
import domain.errors.runtime.LoginCredentialsRuntimeException;
import domain.persistence.repositories.UserRepository;
import domain.requests.posts.RequestPostLogin;
import domain.responses.posts.ResponsePostUserLogin;
import domain.security.contracts.SessionCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = UriConstants.Logins.URL)
public class LoginsController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionCreator sessionCreator;

    @Autowired
    public LoginsController(UserRepository userRepository, PasswordEncoder passwordEncoder, SessionCreator sessionCreator){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionCreator = sessionCreator;
    }

    @PostMapping(consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponsePostUserLogin> post(@Valid @RequestBody RequestPostLogin requestPostLogin) {

        var optionalUser = this.userRepository
                                             .findAll()
                                             .stream()
                                             .filter(u -> requestPostLogin.getEmail().equals(u.getEmail()))
                                             .findFirst();

        optionalUser.orElseThrow(LoginCredentialsRuntimeException::new);

        var user = optionalUser.get();
        if (!this.passwordEncoder.matches(requestPostLogin.getPassword(), user.getPassword())) {
           throw new LoginCredentialsRuntimeException();
        }

        var responseCookie = this.sessionCreator.generateResponseCookie(user);

        ResponsePostUserLogin ResponsePostUserLogin = new ResponsePostUserLogin(user.getName());

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                             .body(ResponsePostUserLogin);
    }
}
