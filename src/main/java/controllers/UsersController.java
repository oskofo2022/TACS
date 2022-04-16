package controllers;
import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.persistence.entities.User;
import domain.persistence.repositories.UserRepository;
import domain.requests.gets.lists.RequestGetListUser;
import domain.requests.posts.RequestPostUser;
import domain.responses.gets.lists.ResponseGetListUser;
import domain.responses.gets.lists.ResponseGetPagedList;
import domain.responses.gets.lists.ResponseGetUser;
import domain.responses.posts.ResponsePostEntityCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping(path = UriConstants.Users.URL)
public class UsersController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(path = UriConstants.Users.ID, produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetUser> get(@PathVariable String userId)
    {
        ResponseGetUser responseGetUser = new ResponseGetUser("some name", "email@email.com");

        return ResponseEntity.ok(responseGetUser);
    }

    @GetMapping(produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListUser>> list(RequestGetListUser requestGetListUser)
    {
        var responsesGetListUser = this.userRepository.findAll().stream().map(u -> new ResponseGetListUser(u.getId(), u.getName(), u.getEmail())).toList();
        /*var responseGetListUser = new ResponseGetListUser(1, "some name", "email@email.com");
        var responsesGetListUser = new ArrayList<ResponseGetListUser>() {
            {
                add(responseGetListUser);
            }
        };*/
        ResponseGetPagedList<ResponseGetListUser> responseGetPagedList = new ResponseGetPagedList<>(1, responsesGetListUser, 1);

        return ResponseEntity.ok(responseGetPagedList);
    }

    @PostMapping(consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponsePostEntityCreation> post(@Valid @RequestBody RequestPostUser requestPostUser)
    {


        var encodedPassword = this.passwordEncoder.encode(requestPostUser.getPassword());

        var user = new User();
        user.setName(requestPostUser.getName());
        user.setEmail(requestPostUser.getEmail());
        user.setName(requestPostUser.getName());
        user.setPassword(encodedPassword);

        this.userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path(UriConstants.Users.ID)
                                                  .buildAndExpand(user.getId())
                                                  .toUri();

        ResponsePostEntityCreation responsePostEntityCreation = new ResponsePostEntityCreation(user.getId());

        return ResponseEntity.created(location)
                             .body(responsePostEntityCreation);
    }
}
