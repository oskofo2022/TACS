package controllers;
import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.requests.posts.RequestPostUser;
import domain.responses.gets.lists.ResponseGetUser;
import domain.responses.posts.ResponsePostEntityCreation;
import org.hibernate.validator.constraints.URL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = UriConstants.Users.URL)
public class UsersController {

    /* To future usages
    private final CrudRepository<User, Long> userRepository;

    @Autowired
    public UsersController(CrudRepository<User, Long> userRepository) {
        this.userRepository = userRepository;
    }*/

    @GetMapping(path = UriConstants.ID, produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetUser> get(@PathVariable String id)
    {
        ResponseGetUser responseGetUser = new ResponseGetUser("some name", "email@email.com");

        return ResponseEntity.ok(responseGetUser);
    }

    @PostMapping(consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponsePostEntityCreation> post(@Valid @RequestBody RequestPostUser requestPostUser)
    {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path(UriConstants.ID)
                                                  .buildAndExpand(1)
                                                  .toUri();

        ResponsePostEntityCreation responsePostEntityCreation = new ResponsePostEntityCreation(1);

        return ResponseEntity.created(location)
                             .body(responsePostEntityCreation);
    }
}
