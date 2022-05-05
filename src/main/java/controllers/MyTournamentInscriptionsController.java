package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.persistence.repositories.InscriptionRepository;
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

    private final InscriptionRepository inscriptionRepository;
    private final WordleAuthenticationManager wordleAuthenticationManager;

    @Autowired
    public MyTournamentInscriptionsController(WordleAuthenticationManager wordleAuthenticationManager, InscriptionRepository inscriptionRepository) {
        this.wordleAuthenticationManager = wordleAuthenticationManager;
        this.inscriptionRepository = inscriptionRepository;
    }

    @GetMapping(produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListUserInscription>> list(RequestGetListUserInscription requestGetListUserInscription)
    {
        final var userId = this.wordleAuthenticationManager.getCurrentUser()
                                                                 .getId();
        requestGetListUserInscription.setUserId(userId);
        var responseGetPagedList = this.list(this.inscriptionRepository,
                                                                                     requestGetListUserInscription,
                                                                                     i -> new ResponseGetListUserInscription(new ResponseGetListTournament(i.getTournament().getId(), i.getTournament().getName(), i.getTournament().getLanguage(), i.getTournament().getVisibility(), i.getTournament().getState(), i.getTournament().getStartDate(), i.getTournament().getEndDate())));

        return ResponseEntity.ok(responseGetPagedList);
    }
}
