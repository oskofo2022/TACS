package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.repositories.entities.*;
import domain.requests.gets.lists.RequestGetListTournament;
import domain.requests.gets.lists.RequestGetPagedList;
import domain.requests.posts.RequestPostInscriptionTournament;
import domain.responses.gets.lists.ResponseGetListTournament;
import domain.responses.gets.lists.ResponseGetPagedList;
import domain.responses.posts.ResponsePostEntityCreation;
import domain.responses.posts.ResponsePostUserInscriptionGameGuess;
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


    /*
    @PostMapping(path = UriConstants.Tournaments.UserTournament.URL, consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponsePostEntityCreation> post(@Valid @RequestBody RequestPostInscriptionTournament requestPostInscriptionTournament){

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(UriConstants.Tournaments.ID).buildAndExpand(1).toUri();

        var user = new User();
        var inscription = new Inscription();
        var inscriptionIdentifier = new InscriptionIdentifier();
        inscriptionIdentifier.setUserId(1);
        inscriptionIdentifier.setTournamentId(22);
        inscription.setInscriptionIdentifier(inscriptionIdentifier);


        var responsesGetListGuessCharMatching =  game.listGuessCharsMatching(requestPostUserGameGuess);

        ResponsePostUserInscriptionGameGuess responsePostUserInscriptionGameGuess = new ResponsePostUserInscriptionGameGuess(1, responsesGetListGuessCharMatching);

        return ResponseEntity.created(location)
                .body(responsePostUserInscriptionGameGuess);


    }
    
     */

}
