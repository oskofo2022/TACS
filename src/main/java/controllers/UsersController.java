package controllers;
import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.errors.runtime.DuplicateEntityFoundRuntimeException;
import domain.errors.runtime.EntityNotFoundRuntimeException;
import domain.persistence.entities.User;
import domain.persistence.queries.SpecificationBuilder;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

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
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseGetUser> get(@PathVariable UUID userId) {

        final var user = this.userRepository.findById(userId)
                                                  .orElseThrow(() -> new EntityNotFoundRuntimeException(User.class));

        final var responseGetUser = new ResponseGetUser(user.getName(), user.getEmail());
        return ResponseEntity.ok(responseGetUser);
    }

    @GetMapping(produces = MediaTypeConstants.JSON)
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListUser>> list(@Valid RequestGetListUser requestGetListUser)
    {
        final var responseGetPagedList = this.list(this.userRepository, requestGetListUser, u -> new ResponseGetListUser(u.getId(), u.getName(), u.getEmail()));
        return ResponseEntity.ok(responseGetPagedList);
    }

    @PostMapping(consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<ResponsePostEntityCreation> post(@Valid @RequestBody RequestPostUser requestPostUser)
    {
        final var specificationBuilder = new SpecificationBuilder();
        specificationBuilder.orEqual("email", requestPostUser.getEmail())
                            .orEqual("name", requestPostUser.getName());


        final var optionalDuplicateUser = this.userRepository.findAll(specificationBuilder.build())
                                                                          .stream()
                                                                          .findAny();

        if (optionalDuplicateUser.isPresent()) {
            throw new DuplicateEntityFoundRuntimeException(User.class);
        }

        final var encodedPassword = this.passwordEncoder.encode(requestPostUser.getPassword());

        final var user = new User();
        user.setName(requestPostUser.getName());
        user.setEmail(requestPostUser.getEmail());
        user.setName(requestPostUser.getName());
        user.setPassword(encodedPassword);

        this.userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path(UriConstants.Users.ID)
                                                  .buildAndExpand(user.getId())
                                                  .toUri();

        final var responsePostEntityCreation = new ResponsePostEntityCreation(user.getId());

        return ResponseEntity.created(location)
                             .body(responsePostEntityCreation);
    }
}
