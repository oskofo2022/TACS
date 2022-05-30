package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.persistence.sessions.UserContextService;
import domain.requests.gets.lists.RequestGetListUserInscription;
import domain.responses.gets.lists.*;
import domain.security.WordleAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = UriConstants.Users.Myself.Inscriptions.Tournaments.URL)
public class MyTournamentInscriptionsController {

    private final UserContextService userContextService;

    @Autowired
    public MyTournamentInscriptionsController(UserContextService userContextService) {
        this.userContextService = userContextService;
    }

    @GetMapping(produces = MediaTypeConstants.JSON)
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListUserInscription>> list(@Valid RequestGetListUserInscription requestGetListUserInscription)
    {
        final var user = this.userContextService.get();

        var responseGetPagedList = requestGetListUserInscription.paginate(() -> user.getInscribedTournaments().stream(),
                                                                                                                  t -> new ResponseGetListUserInscription(new ResponseGetListTournament(t.getId(), t.getName(), t.getLanguage(), t.getVisibility(), t.getState(), t.getStartDate(), t.getEndDate())));

        return ResponseEntity.ok(responseGetPagedList);
    }
}
