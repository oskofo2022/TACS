package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.persistence.repositories.TournamentRepository;
import domain.requests.gets.lists.RequestGetListUserInscription;
import domain.responses.gets.lists.*;
import domain.security.WordleAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = UriConstants.Users.Myself.Inscriptions.Tournaments.URL)
public class MyTournamentInscriptionsController extends PagedListController{

    private final TournamentRepository tournamentRepository;
    private final WordleAuthenticationManager wordleAuthenticationManager;

    @Autowired
    public MyTournamentInscriptionsController(TournamentRepository tournamentRepository, WordleAuthenticationManager wordleAuthenticationManager) {
        this.tournamentRepository = tournamentRepository;
        this.wordleAuthenticationManager = wordleAuthenticationManager;
    }

    @GetMapping(produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListUserInscription>> list(RequestGetListUserInscription requestGetListUserInscription)
    {
        final var userId = this.wordleAuthenticationManager.getCurrentUser()
                                                                 .getId();
        requestGetListUserInscription.setUserId(userId);
        var responseGetPagedList = this.list(this.tournamentRepository,
                                                                                     requestGetListUserInscription,
                                                                                     t -> new ResponseGetListUserInscription(new ResponseGetListTournament(t.getId(), t.getName(), t.getLanguage(), t.getVisibility(), t.getState(), t.getStartDate(), t.getEndDate())));

        return ResponseEntity.ok(responseGetPagedList);
    }
}
