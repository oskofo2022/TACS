package controllers;

import constants.UriConstants;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.sessions.UserContextService;
import domain.requests.gets.lists.RequestGetListMyTournament;
import domain.requests.gets.lists.RequestGetListTournament;
import domain.responses.gets.lists.ResponseGetListTournament;
import domain.responses.gets.lists.ResponseGetPagedList;
import domain.security.WordleAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = UriConstants.Users.Myself.Tournaments.URL)
public class MyTournamentsController extends PagedListController {
    private final WordleAuthenticationManager wordleAuthenticationManager;
    private final TournamentRepository tournamentRepository;

    @Autowired
    public MyTournamentsController(WordleAuthenticationManager wordleAuthenticationManager, TournamentRepository tournamentRepository) {
        this.wordleAuthenticationManager = wordleAuthenticationManager;
        this.tournamentRepository = tournamentRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListTournament>> list(@Valid RequestGetListMyTournament requestGetListMyTournament) {
        final var wordleUser = this.wordleAuthenticationManager.getCurrentUser();
        requestGetListMyTournament.setUserCreatorId(wordleUser.getId());

        final var responseGetPagedList = this.list(this.tournamentRepository,
                                                                                          requestGetListMyTournament,
                                                                                          t -> new ResponseGetListTournament(t.getId(), t.getName(), t.getLanguage(), t.getVisibility(), t.getState(), t.getStartDate(), t.getEndDate()));

        return ResponseEntity.ok(responseGetPagedList);
    }
}
