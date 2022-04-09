package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.repositories.entities.Language;
import domain.repositories.entities.TournamentState;
import domain.repositories.entities.Visibility;
import domain.requests.gets.lists.RequestGetListUserInscription;
import domain.responses.gets.lists.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

@RestController
@RequestMapping(path = UriConstants.Users.Myself.Inscriptions.Tournaments.URL)
public class UsersMyselfInscriptionsController {
    @GetMapping(produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListUserInscription>> list(RequestGetListUserInscription requestGetListUserInscription)
    {

        var localDate = LocalDate.now();
        var responseGetListTournament = new ResponseGetListTournament(1, "tournament", Language.ENGLISH, Visibility.PUBLIC, TournamentState.STARTED, localDate, localDate);
        var responsesGetListGameGuess = new ArrayList<ResponseGetListGameGuess>() {
            {
                add(new ResponseGetListGameGuess("lord", new ArrayList<>() {
                    {
                        add(new ResponseGetListGuessCharMatching('l', CharProximity.NONE));
                        add(new ResponseGetListGuessCharMatching('o', CharProximity.HIT));
                        add(new ResponseGetListGuessCharMatching('r', CharProximity.HIT));
                        add(new ResponseGetListGuessCharMatching('d', CharProximity.HIT));
                    }
                }));
                add(new ResponseGetListGameGuess("bord", new ArrayList<>() {
                    {
                        add(new ResponseGetListGuessCharMatching('b', CharProximity.NONE));
                        add(new ResponseGetListGuessCharMatching('o', CharProximity.HIT));
                        add(new ResponseGetListGuessCharMatching('r', CharProximity.HIT));
                        add(new ResponseGetListGuessCharMatching('d', CharProximity.HIT));
                    }
                }));
                add(new ResponseGetListGameGuess("word", new ArrayList<>() {
                    {
                        add(new ResponseGetListGuessCharMatching('w', CharProximity.HIT));
                        add(new ResponseGetListGuessCharMatching('o', CharProximity.HIT));
                        add(new ResponseGetListGuessCharMatching('r', CharProximity.HIT));
                        add(new ResponseGetListGuessCharMatching('d', CharProximity.HIT));
                    }
                }));
            }
        };
        var responsesGetListUserInscriptionGame = new ArrayList<ResponseGetListUserInscriptionGame>() {
            {
                add(new ResponseGetListUserInscriptionGame("word", true, responsesGetListGameGuess));
            }
        };
        var responseGetListUserInscription = new ResponseGetListUserInscription(responseGetListTournament, responsesGetListUserInscriptionGame);

        var responsesGetListUserInscription = new ArrayList<ResponseGetListUserInscription>() {
            {
                add(responseGetListUserInscription);
            }
        };
        ResponseGetPagedList<ResponseGetListUserInscription> responseGetPagedList = new ResponseGetPagedList<>(1, responsesGetListUserInscription, 1);

        return ResponseEntity.ok(responseGetPagedList);
    }

}
