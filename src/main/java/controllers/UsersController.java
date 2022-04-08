package controllers;
import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.requests.gets.lists.RequestGetListUser;
import domain.requests.posts.RequestPostUser;
import domain.responses.gets.lists.ResponseGetListUser;
import domain.responses.gets.lists.ResponseGetPagedList;
import domain.responses.gets.lists.ResponseGetUser;
import domain.responses.posts.ResponsePostEntityCreation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;

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

    @GetMapping(produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListUser>> list(RequestGetListUser requestGetListUser)
    {

        var responseGetListUser = new ResponseGetListUser();
        responseGetListUser.setId(1);
        responseGetListUser.setName("some name");
        responseGetListUser.setEmail("email@email.com");

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
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path(UriConstants.ID)
                                                  .buildAndExpand(1)
                                                  .toUri();

        ResponsePostEntityCreation responsePostEntityCreation = new ResponsePostEntityCreation(1);

        return ResponseEntity.created(location)
                             .body(responsePostEntityCreation);
    }
}
