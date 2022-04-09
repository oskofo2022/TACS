package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.repositories.entities.Game;
import domain.responses.gets.ResponseGetInscriptionTournamentGame;
import domain.requests.posts.RequestPostUserInscriptionGameGuess;
import domain.responses.gets.lists.CharProximity;
import domain.responses.gets.lists.ResponseGetListGameGuess;
import domain.responses.common.gets.ResponseCommonGetGuessCharMatching;
import domain.responses.posts.ResponsePostUserInscriptionGameGuess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping(path = UriConstants.Users.Myself.Inscriptions.Tournaments.Games.URL)
public class MyTournamentGamesController {

    @PostMapping(consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponsePostUserInscriptionGameGuess> post(@PathVariable String tournamentId, @Valid @RequestBody RequestPostUserInscriptionGameGuess requestPostUserGameGuess)
    {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path(UriConstants.Users.Myself.Inscriptions.Tournaments.Games.ID)
                                                  .buildAndExpand(1)
                                                  .toUri();

        var game = new Game();
        game.setWord("word");

        var responsesGetListGuessCharMatching =  game.listGuessCharsMatching(requestPostUserGameGuess);

        ResponsePostUserInscriptionGameGuess responsePostUserInscriptionGameGuess = new ResponsePostUserInscriptionGameGuess(1, responsesGetListGuessCharMatching);

        return ResponseEntity.created(location)
                             .body(responsePostUserInscriptionGameGuess);
    }

    @GetMapping(path = UriConstants.Users.Myself.Inscriptions.Tournaments.Games.ID, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseGetInscriptionTournamentGame> get(@PathVariable String tournamentId, @PathVariable String gameId)
    {
        var responsesGetListGameGuess = new ArrayList<ResponseGetListGameGuess>() {
            {
                add(new ResponseGetListGameGuess("lord", new ArrayList<>() {
                    {
                        add(new ResponseCommonGetGuessCharMatching('l', CharProximity.NONE));
                        add(new ResponseCommonGetGuessCharMatching('o', CharProximity.HIT));
                        add(new ResponseCommonGetGuessCharMatching('r', CharProximity.HIT));
                        add(new ResponseCommonGetGuessCharMatching('d', CharProximity.HIT));
                    }
                }));
                add(new ResponseGetListGameGuess("bord", new ArrayList<>() {
                    {
                        add(new ResponseCommonGetGuessCharMatching('b', CharProximity.NONE));
                        add(new ResponseCommonGetGuessCharMatching('o', CharProximity.HIT));
                        add(new ResponseCommonGetGuessCharMatching('r', CharProximity.HIT));
                        add(new ResponseCommonGetGuessCharMatching('d', CharProximity.HIT));
                    }
                }));
                add(new ResponseGetListGameGuess("word", new ArrayList<>() {
                    {
                        add(new ResponseCommonGetGuessCharMatching('w', CharProximity.HIT));
                        add(new ResponseCommonGetGuessCharMatching('o', CharProximity.HIT));
                        add(new ResponseCommonGetGuessCharMatching('r', CharProximity.HIT));
                        add(new ResponseCommonGetGuessCharMatching('d', CharProximity.HIT));
                    }
                }));
            }
        };

        var responseGetInscriptionTournamentGame = new ResponseGetInscriptionTournamentGame("word", responsesGetListGameGuess);

        return ResponseEntity.ok(responseGetInscriptionTournamentGame);
    }
}
