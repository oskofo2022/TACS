package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.repositories.entities.Language;
import domain.repositories.entities.TournamentState;
import domain.repositories.entities.Visibility;
import domain.requests.gets.lists.RequestGetListPublicTournament;
import domain.requests.gets.lists.RequestGetListTournament;
import domain.requests.posts.RequestPostTournament;
import domain.responses.gets.lists.ResponseGetListTournament;
import domain.responses.gets.lists.ResponseGetPagedList;
import domain.responses.posts.ResponsePostTournament;
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
public class TournamentsController {

    // Only public's tournaments, was thinked for general usage without authentication
    @GetMapping(path = UriConstants.Tournaments.PUBLIC, produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListTournament>> listPublic(RequestGetListPublicTournament requestGetListPublicTournament) {

        var responseGetListTournament = new ResponseGetListTournament(23, "Torneo Regional", Language.SPANISH,
                Visibility.PUBLIC, TournamentState.READY, LocalDate.of(2022, 4, 9),
                LocalDate.of(2022, 4, 10));
        var responsesGetListTournament = new ArrayList<ResponseGetListTournament>();
        responsesGetListTournament.add(responseGetListTournament);

        ResponseGetPagedList<ResponseGetListTournament> responseGetPagedList = new ResponseGetPagedList<>(1, responsesGetListTournament, 1);

        return ResponseEntity.ok(responseGetPagedList);
    }

    @GetMapping(produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListTournament>> list(RequestGetListTournament requestGetListTournament) {

        var responseGetListTournament = new ResponseGetListTournament(23, "Torneo Regional", Language.SPANISH,
                Visibility.PUBLIC, TournamentState.READY, LocalDate.of(2022, 4, 9),
                LocalDate.of(2022, 4, 10));
        var responsesGetListTournament = new ArrayList<ResponseGetListTournament>();
        responsesGetListTournament.add(responseGetListTournament);

        ResponseGetPagedList<ResponseGetListTournament> responseGetPagedList = new ResponseGetPagedList<>(1, responsesGetListTournament, 1);

        return ResponseEntity.ok(responseGetPagedList);
    }


    @PostMapping(produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponsePostTournament> post(@Valid @RequestBody RequestPostTournament requestPostTournament) {

        long tournamentId = 1;

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path(UriConstants.Tournaments.ID)
                                                  .buildAndExpand(1)
                                                  .toUri();

        ResponsePostTournament responsePostTournament = new ResponsePostTournament(
                tournamentId,
                requestPostTournament.getName(),
                requestPostTournament.getLang(),
                requestPostTournament.getVisibility(),
                requestPostTournament.getBeginDate(),
                requestPostTournament.getEndDate()
        );

        return ResponseEntity.created(location)
                             .body(responsePostTournament);
    }
}