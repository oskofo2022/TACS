package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.errors.constants.ErrorMessageConstants;
import domain.errors.runtime.EntityNotFoundRuntimeException;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.repositories.UserRepository;
import domain.requests.posts.RequestPostTournamentInscription;
import domain.security.WordleAuthenticationManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = UriConstants.Tournaments.Inscriptions.URL)
public class TournamentPrivateInscriptionsController extends TournamentInscriptionsController {

    private final UserRepository userRepository;
    private final WordleAuthenticationManager wordleAuthenticationManager;


    public TournamentPrivateInscriptionsController(TournamentRepository tournamentRepository, UserRepository userRepository, WordleAuthenticationManager wordleAuthenticationManager) {
        super(tournamentRepository);
        this.userRepository = userRepository;
        this.wordleAuthenticationManager = wordleAuthenticationManager;
    }

    @PostMapping(produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<Void> post(@PathVariable UUID tournamentId, @Valid @RequestBody RequestPostTournamentInscription requestPostTournamentInscription) {
        final var tournament = this.tournamentRepository.findById(tournamentId)
                                                                   .orElseThrow(() -> new EntityNotFoundRuntimeException(ErrorMessageConstants.Entities.Names.TOURNAMENT, Tournament.class));

        final var user = this.userRepository.findById(requestPostTournamentInscription.userId)
                                                  .orElseThrow(() -> new EntityNotFoundRuntimeException(ErrorMessageConstants.Entities.Names.USER, User.class));

        tournament.validateAuthority(this.wordleAuthenticationManager.getCurrentUser());

        return this.inscribe(user, tournament);
    }
}
