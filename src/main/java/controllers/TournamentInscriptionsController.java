package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.responses.posts.ResponsePostTournament;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping(UriConstants.Tournaments.Inscriptions.URL)
public class TournamentInscriptionsController {

    @PostMapping(path = UriConstants.Tournaments.Inscriptions.CURRENT_USER, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponsePostTournament> post(@PathVariable long tournamentId) {

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path(UriConstants.DELIMITER + UriConstants.Users.Myself.Inscriptions.Tournaments.URL)
                                                                           .query(UriConstants.Users.Myself.Inscriptions.Tournaments.Queries.ID)
                                                                           .buildAndExpand(tournamentId)
                                                                           .toUri();

        return ResponseEntity.created(location)
                             .build();
    }
}
