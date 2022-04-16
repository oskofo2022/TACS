package controllers;

import constants.UriConstants;
import constants.MediaTypeConstants;
import domain.requests.posts.RequestPostLogin;
import domain.responses.posts.ResponsePostUserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import domain.security.JwtUtils;

import javax.validation.Valid;

@RestController
@RequestMapping(path = UriConstants.Logins.URL)
public class LoginsController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public LoginsController(JwtUtils jwtUtils, AuthenticationManager authenticationManager){
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }



    @PostMapping(consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponsePostUserLogin> post(@Valid @RequestBody RequestPostLogin requestPostLogin) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestPostLogin.getEmail(), requestPostLogin.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password.", e);
        }

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(requestPostLogin.getEmail());

        ResponsePostUserLogin ResponsePostUserLogin = new ResponsePostUserLogin(1, "juan");

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                             .body(ResponsePostUserLogin);
    }
}
