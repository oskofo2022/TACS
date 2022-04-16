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
import org.springframework.web.bind.annotation.*;
import domain.security.JwtUtils;

import javax.validation.Valid;

@RestController
@RequestMapping(path = UriConstants.Logins.URL)
public class LoginsController {

    private final JwtUtils jwtUtils;

    @Autowired
    public LoginsController(JwtUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    @PostMapping(consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponsePostUserLogin> post(@Valid @RequestBody RequestPostLogin requestPostLogin) {

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(requestPostLogin.getEmail());

        ResponsePostUserLogin ResponsePostUserLogin = new ResponsePostUserLogin(1, "juan");

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                             .body(ResponsePostUserLogin);
    }
}
