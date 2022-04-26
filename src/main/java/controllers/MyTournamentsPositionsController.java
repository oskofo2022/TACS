package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.responses.gets.lists.ResponseGetListTournamentPosition;
import domain.responses.gets.lists.ResponseGetListTournamentPositionResult;
import domain.security.WordleAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = UriConstants.Users.Myself.Inscriptions.Tournaments.Positions.URL)
public class MyTournamentsPositionsController {
    private final WordleAuthenticationManager wordleAuthenticationManager;

    @Autowired
    public MyTournamentsPositionsController(WordleAuthenticationManager wordleAuthenticationManager) {
        this.wordleAuthenticationManager = wordleAuthenticationManager;
    }

    @GetMapping(produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetListTournamentPosition> list() {
        final var user = this.wordleAuthenticationManager.getCurrentUser();

        //TODO: Add logic to summarize each position based on guesses count ordered by the sum column, after that pre filter the query with the user id, consider pagination (possible manual query to optimize API response times)

        final var responsesGetListTournamentPositionResult = new ArrayList<ResponseGetListTournamentPositionResult>() {
            {
                add(new ResponseGetListTournamentPositionResult("player 1", 100));
                add(new ResponseGetListTournamentPositionResult("player 2", 49));
                add(new ResponseGetListTournamentPositionResult("player 3", 31));
            }
        };
        final var responseGetListTournamentPosition = new ResponseGetListTournamentPosition(responsesGetListTournamentPositionResult);
        return ResponseEntity.ok(responseGetListTournamentPosition);
    }
}
