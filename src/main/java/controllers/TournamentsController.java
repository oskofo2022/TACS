package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.repositories.entities.*;
import domain.requests.gets.lists.RequestGetListTournament;
import domain.responses.gets.lists.ResponseGetListTournament;
import domain.responses.gets.lists.ResponseGetPagedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping(path = UriConstants.Tournaments.URL)
public class TournamentsController {

    // Only public's tournaments
    @GetMapping(path = UriConstants.Tournaments.PUBLIC, produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListTournament>> list(RequestGetListTournament requestGetListTournament){

        var responseGetListTournament = new ResponseGetListTournament(23, "Torneo Regional", Language.SPANISH,
                Visibility.PUBLIC, TournamentState.READY, LocalDate.of(2022, 4, 9),
                LocalDate.of(2022, 4, 10));
        var responsesGetListTournament = new ArrayList<ResponseGetListTournament>();
        responsesGetListTournament.add(responseGetListTournament);

        ResponseGetPagedList<ResponseGetListTournament> responseGetPagedList = new ResponseGetPagedList<>(1,responsesGetListTournament,1);

        return ResponseEntity.ok(responseGetPagedList);
    }


    @PostMapping(path = UriConstants.Tournaments.UserTournament.URL, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity post(){

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(UriConstants.Tournaments.URL)
                        .buildAndExpand(1).toUri();

        return ResponseEntity.created(location).build();

    }

}
