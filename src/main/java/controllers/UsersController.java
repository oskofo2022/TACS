package controllers;
import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.errors.runtime.EntityNotFoundRuntimeException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;


@RestController
@RequestMapping(path = UriConstants.Users.URL)
public class UsersController extends PagedListController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UsersController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(path = UriConstants.Users.ID, produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetUser> get(@PathVariable Long userId) {

        var user = this.userRepository.findById(userId)
                                            .orElseThrow(() -> new EntityNotFoundRuntimeException("User not found", User.class));

        ResponseGetUser responseGetUser = new ResponseGetUser(user.getName(), user.getEmail());
        return ResponseEntity.ok(responseGetUser);
    }

    @GetMapping(produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListUser>> list(@Valid RequestGetListUser requestGetListUser)
    {
        var responseGetPagedList = this.list(this.userRepository, requestGetListUser, u -> new ResponseGetListUser(u.getId(), u.getName(), u.getEmail()));
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
