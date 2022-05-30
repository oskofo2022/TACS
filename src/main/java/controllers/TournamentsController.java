package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.sessions.UserContextService;
import domain.requests.gets.lists.RequestGetListPublicTournament;
import domain.requests.posts.RequestPostTournament;
import domain.responses.gets.lists.ResponseGetListTournament;
import domain.responses.gets.lists.ResponseGetPagedList;
import domain.responses.posts.ResponsePostEntityCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(path = UriConstants.Tournaments.URL)
public class TournamentsController extends PagedListController {

    private final TournamentRepository tournamentRepository;
    private final UserContextService userContextService;

    @Autowired
    public TournamentsController(TournamentRepository tournamentRepository, UserContextService userContextService) {
        this.tournamentRepository = tournamentRepository;
        this.userContextService = userContextService;
    }

    // Only public's tournaments, was thinked for general usage without authentication
    @GetMapping(path = UriConstants.Tournaments.Public.SUBTYPE, produces = MediaTypeConstants.JSON)
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListTournament>> listPublic(@Valid RequestGetListPublicTournament requestGetListPublicTournament) {

        var responseGetPagedList = this.list(this.tournamentRepository,
                                                                                    requestGetListPublicTournament,
                                                                                    t -> new ResponseGetListTournament(t.getId(), t.getName(), t.getLanguage(), t.getVisibility(), t.getState(), t.getStartDate(), t.getEndDate()));

        return ResponseEntity.ok(responseGetPagedList);
    }

    @PostMapping(produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<ResponsePostEntityCreation> post(@Valid @RequestBody RequestPostTournament requestPostTournament) {

        final var user = this.userContextService.get();

        var tournament = user.createTournament(requestPostTournament);

        this.tournamentRepository.save(tournament);

        final var location = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                             .path(UriConstants.DELIMITER + UriConstants.Users.Myself.Tournaments.URL)
                                                             .query(UriConstants.Queries.ID)
                                                             .buildAndExpand(tournament.getId())
                                                             .toUri();

        final var responsePostEntityCreation = new ResponsePostEntityCreation(tournament.getId());

        return ResponseEntity.created(location)
                             .body(responsePostEntityCreation);
    }
}