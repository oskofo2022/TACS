package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.persistence.entities.Inscription;
import domain.persistence.sessions.UserContextService;
import domain.requests.gets.lists.RequestGetListTournamentPosition;
import domain.responses.gets.lists.ResponseGetListTournamentPosition;
import domain.responses.gets.lists.ResponseGetPagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = UriConstants.Users.Myself.Inscriptions.Tournaments.Positions.URL)
public class MyTournamentsPositionsController {

    private final UserContextService userContextService;

    @Autowired
    public MyTournamentsPositionsController(UserContextService userContextService) {
        this.userContextService = userContextService;
    }

    @GetMapping(produces = MediaTypeConstants.JSON)
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListTournamentPosition>> list(@Valid RequestGetListTournamentPosition requestGetListTournamentPosition) {
        final var user = this.userContextService.get();

        final var responseGetPagedList = requestGetListTournamentPosition.paginate(() -> user.getInscriptions()
                                                                                                                                         .stream()
                                                                                                                                         .map(Inscription::getTournament),
                                                                                                                               t -> new ResponseGetListTournamentPosition(t.getName(), t.getState(), t.getStartDate(), t.getEndDate(), t.listPositions()));
        return ResponseEntity.ok(responseGetPagedList);
    }
}
