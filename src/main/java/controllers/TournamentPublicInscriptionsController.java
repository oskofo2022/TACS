package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.errors.runtime.EntityNotFoundRuntimeException;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.enums.Visibility;
import domain.persistence.repositories.InscriptionRepository;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.sessions.UserContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = UriConstants.Tournaments.Public.Inscriptions.URL)
public class TournamentPublicInscriptionsController extends TournamentInscriptionsController {
    private final TournamentRepository tournamentRepository;
    private final UserContextService userContextService;

    @Autowired
    public TournamentPublicInscriptionsController(InscriptionRepository inscriptionRepository, TournamentRepository tournamentRepository, UserContextService userContextService) {
        super(inscriptionRepository);
        this.tournamentRepository = tournamentRepository;
        this.userContextService = userContextService;
    }

    @PostMapping(path = UriConstants.Tournaments.Public.Inscriptions.CURRENT_USER, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> post(@PathVariable long tournamentId) {
        final var user = this.userContextService.get();

        final var tournament = this.tournamentRepository.findById(tournamentId)
                                                                   .filter(t -> t.getVisibility() == Visibility.PUBLIC)
                                                                   .orElseThrow(() -> new EntityNotFoundRuntimeException(Tournament.class));

        return this.inscribe(user, tournament);
    }
}
