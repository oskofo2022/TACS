package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.repositories.UserRepository;
import domain.requests.gets.lists.RequestGetListPublicTournament;
import domain.requests.gets.lists.RequestGetListTournament;
import domain.requests.posts.RequestPostTournament;
import domain.responses.gets.lists.ResponseGetListTournament;
import domain.responses.gets.lists.ResponseGetPagedList;
import domain.responses.posts.ResponsePostEntityCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;



@RestController
@RequestMapping(path = UriConstants.Tournaments.URL)
public class TournamentsController extends PagedListController{

    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    @Autowired
    public TournamentsController(TournamentRepository tournamentRepository, UserRepository userRepository) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
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

        var responseGetPagedList = this.list(this.tournamentRepository, requestGetListTournament,
                t -> new ResponseGetListTournament(t.getId(), t.getName(),t.getLanguage(),t.getVisibility(),t.getState(),t.getStartDate(),t.getEndDate()));
        return ResponseEntity.ok(responseGetPagedList);

    }


    @PostMapping(produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponsePostEntityCreation> post(@Valid @RequestBody RequestPostTournament requestPostTournament) {

        // TODO: Take the ID user from token session
        var user = new User();
        user = this.userRepository.findById(Integer.toUnsignedLong(1)).get();

        var tournament = new Tournament();
        tournament.setName(requestPostTournament.getName());
        tournament.setEndDate(requestPostTournament.getEndDate());
        tournament.setStartDate(requestPostTournament.getBeginDate());
        tournament.setLanguage(requestPostTournament.getLang());
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