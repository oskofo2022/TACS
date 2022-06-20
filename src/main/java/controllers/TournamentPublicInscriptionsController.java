package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.errors.constants.ErrorMessageConstants;
import domain.errors.runtime.EntityNotFoundRuntimeException;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.enums.Visibility;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.sessions.UserContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = UriConstants.Tournaments.Public.Inscriptions.URL)
public class TournamentPublicInscriptionsController extends TournamentInscriptionsController {

    private final UserContextService userContextService;

    @Autowired
    public TournamentPublicInscriptionsController(TournamentRepository tournamentRepository, UserContextService userContextService) {
        super(tournamentRepository);
        this.userContextService = userContextService;
    }

    @PostMapping(path = UriConstants.Tournaments.Public.Inscriptions.CURRENT_USER, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<Void> post(@PathVariable UUID tournamentId) {
        final var user = this.userContextService.get();

        final var tournament = this.tournamentRepository.findById(tournamentId)
                                                                   .filter(t -> t.getVisibility() == Visibility.PUBLIC)
                                                                   .orElseThrow(() -> new EntityNotFoundRuntimeException(ErrorMessageConstants.Entities.Names.TOURNAMENT, Tournament.class));

        return this.inscribe(user, tournament);
    }
}
