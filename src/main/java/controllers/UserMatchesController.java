package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.requests.posts.RequestPostUserMatchToday;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = UriConstants.Users.Myself.Matches.Today.URL)
public class UserMatchesController {
    @PostMapping(consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
    public ResponseEntity<Void> post(@RequestBody RequestPostUserMatchToday requestPostUserMatchToday) {
        //TODO: Save games for each language and each inscription of user, creating the match record with guesses count, this will repeat for each tournament

        return ResponseEntity.ok()
                             .build();
    }
}
