package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.repositories.entities.Game;
import domain.requests.posts.RequestPostUserInscriptionGameGuess;
import domain.responses.posts.ResponsePostUserInscriptionGameGuess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = UriConstants.Users.Myself.Inscriptions.Tournaments.Games.URL)
public class UsersMyselfInscriptionsGamesController {
    @PostMapping(consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponsePostUserInscriptionGameGuess> post(@PathVariable String tournamentId, @Valid @RequestBody RequestPostUserInscriptionGameGuess requestPostUserGameGuess)
    {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path(UriConstants.Users.ID)
                                                  .buildAndExpand(1)
                                                  .toUri();

        var game = new Game();
        game.setWord("word");

        var responsesGetListGuessCharMatching =  game.listGuessCharsMatching(requestPostUserGameGuess);

        ResponsePostUserInscriptionGameGuess responsePostUserInscriptionGameGuess = new ResponsePostUserInscriptionGameGuess(1, responsesGetListGuessCharMatching);

        return ResponseEntity.created(location)
                             .body(responsePostUserInscriptionGameGuess);
    }
}
