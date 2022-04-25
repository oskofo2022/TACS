package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.errors.runtime.EntityNotFoundRuntimeException;
import domain.persistence.entities.Game;
import domain.persistence.entities.Guess;
import domain.persistence.entities.Tournament;
import domain.persistence.repositories.GamesRepository;
import domain.persistence.repositories.TournamentRepository;
import domain.requests.posts.RequestPostUserInscriptionGameGuess;
import domain.responses.posts.ResponsePostUserInscriptionGameGuess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = UriConstants.Users.Myself.Inscriptions.Tournaments.Games.Guesses.URL)
public class MyTournamentGameGuessesController extends PagedListController {

    private final TournamentRepository tournamentRepository;

    private final GamesRepository gamesRepository;


    @Autowired
    public MyTournamentGameGuessesController(TournamentRepository tournamentRepository, GamesRepository gamesRepository) {
        this.tournamentRepository = tournamentRepository;
        this.gamesRepository = gamesRepository;
    }


    @PostMapping(consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponsePostUserInscriptionGameGuess> post(@PathVariable String tournamentId, @PathVariable String gameId, @Valid @RequestBody RequestPostUserInscriptionGameGuess requestPostUserGameGuess)
    {
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                  .path(UriConstants.Users.Myself.Inscriptions.Tournaments.Games.URL + UriConstants.Users.Myself.Inscriptions.Tournaments.Games.ID)
                                                  .buildAndExpand(tournamentId, gameId)
                                                  .toUri();

        var tournament = this.tournamentRepository.findById(Long.parseLong(tournamentId))
                .orElseThrow(() -> new EntityNotFoundRuntimeException(Tournament.class));

        var game = this.gamesRepository.findById(Long.parseLong(gameId)).
                orElseThrow(() -> new EntityNotFoundRuntimeException(Game.class));

        Guess guess = new Guess();
        guess.setGame(game);
        guess.setWord(requestPostUserGameGuess.getWord());

        game.getGuesses().add(guess);

        var responsesGetListGuessCharMatching = game.listGuessCharsMatching(requestPostUserGameGuess);

        ResponsePostUserInscriptionGameGuess responsePostUserInscriptionGameGuess = new ResponsePostUserInscriptionGameGuess(game.getId(), responsesGetListGuessCharMatching);

        this.gamesRepository.save(game);

        return ResponseEntity.created(location)
                .body(responsePostUserInscriptionGameGuess);
    }
}
