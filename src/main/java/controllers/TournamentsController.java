package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.repositories.UserRepository;
import domain.requests.gets.lists.RequestGetListPublicTournament;
import domain.requests.gets.lists.RequestGetListTournament;
import domain.requests.posts.RequestPostTournament;
import domain.responses.gets.lists.ResponseGetListTournament;
import domain.responses.gets.lists.ResponseGetPagedList;
import domain.responses.posts.ResponsePostEntityCreation;
import domain.security.WordleAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;



@RestController
@RequestMapping(path = UriConstants.Tournaments.URL)
public class TournamentsController extends PagedListController{

    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final WordleAuthenticationManager wordleAuthenticationManager;

    @Autowired
    public TournamentsController(TournamentRepository tournamentRepository, UserRepository userRepository, WordleAuthenticationManager wordleAuthenticationManager) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
        this.wordleAuthenticationManager = wordleAuthenticationManager;
    }

    // Only public's tournaments, was thinked for general usage without authentication
    @GetMapping(path = UriConstants.Tournaments.PUBLIC, produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListTournament>> listPublic(RequestGetListPublicTournament requestGetListPublicTournament) {

        var responseGetPagedList = this.list(this.tournamentRepository, requestGetListPublicTournament,
                                            t -> new ResponseGetListTournament(t.getId(), t.getName(),t.getLanguage(),t.getVisibility(),t.getState(),t.getStartDate(),t.getEndDate()));

        return ResponseEntity.ok(responseGetPagedList);
    }

    @GetMapping(produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListTournament>> list(RequestGetListTournament requestGetListTournament) {

        /*
        final var wordleUser = this.wordleAuthenticationManager.getCurrentUser();

        var user = new User();
        user = this.userRepository.findById(wordleUser.getId()).get();
         */

        // To debugging purposes we use hardcoded id
        requestGetListTournament.setIdUserCreator(1);
        var responseGetPagedList = this.list(this.tournamentRepository, requestGetListTournament,
                t -> new ResponseGetListTournament(t.getId(), t.getName(),t.getLanguage(),t.getVisibility(),t.getState(),t.getStartDate(),t.getEndDate()));
        return ResponseEntity.ok(responseGetPagedList);

    }


    @PostMapping(produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponsePostEntityCreation> post(@Valid @RequestBody RequestPostTournament requestPostTournament) {


        // To debugging purposes:
        var user = new User();
        user = this.userRepository.findById(1L).get();


        /*
        final var wordleUser = this.wordleAuthenticationManager.getCurrentUser();

        var user = new User();
        user = this.userRepository.findById(wordleUser.getId()).get();
         */

        var tournament = new Tournament();
        tournament.setName(requestPostTournament.getName());
        tournament.setEndDate(requestPostTournament.getEndDate());
        tournament.setStartDate(requestPostTournament.getStartDate());
        tournament.setLanguage(requestPostTournament.getLanguage());
        tournament.setVisibility(requestPostTournament.getVisibility());
        tournament.setState(TournamentState.READY);
        tournament.setUserCreator(user);

        this.tournamentRepository.save(tournament);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(UriConstants.Tournaments.ID)
                .buildAndExpand(1)
                .toUri();

        ResponsePostEntityCreation responsePostEntityCreation = new ResponsePostEntityCreation(tournament.getId());

        return ResponseEntity.created(location)
                             .body(responsePostEntityCreation);
    }
}