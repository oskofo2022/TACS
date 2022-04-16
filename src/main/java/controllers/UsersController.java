package controllers;
import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.repositories.UserRepository;
import domain.repositories.entities.User;
import domain.requests.gets.lists.RequestGetListUser;
import domain.requests.posts.RequestPostUser;
import domain.responses.gets.lists.ResponseGetListUser;
import domain.responses.gets.lists.ResponseGetPagedList;
import domain.responses.gets.lists.ResponseGetUser;
import domain.responses.posts.ResponsePostEntityCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@RestController
@RequestMapping(path = UriConstants.Users.URL)
public class UsersController {

    private final UserRepository userRepository;

    @Autowired
    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        var responseGetListUser = new ResponseGetListUser(1, "some name", "email@email.com");
        var responsesGetListUser = new ArrayList<ResponseGetListUser>() {
            {
                add(responseGetListUser);
            }
        };
        ResponseGetPagedList<ResponseGetListUser> responseGetPagedList = new ResponseGetPagedList<>(1, responsesGetListUser, 1);

        return ResponseEntity.ok(responseGetPagedList);
    }

    @PostMapping(consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponsePostEntityCreation> post(@Valid @RequestBody RequestPostUser requestPostUser)
    {
        //TODO This should not be here
        User user = new User();
        user.setEmail(requestPostUser.getEmail());
        user.setPassword(requestPostUser.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setName(requestPostUser.getName());
        user.setSalt(requestPostUser.getPassword().getBytes(StandardCharsets.UTF_8));

        userRepository.saveAndFlush(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(UriConstants.Users.ID)
                .buildAndExpand(user.getId())
                .toUri();

        ResponsePostEntityCreation responsePostEntityCreation = new ResponsePostEntityCreation(user.getId());

        return ResponseEntity.created(location)
                             .body(responsePostEntityCreation);
    }
}
