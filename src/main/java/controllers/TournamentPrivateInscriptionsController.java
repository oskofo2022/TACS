package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.errors.runtime.EntityNotFoundRuntimeException;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.repositories.InscriptionRepository;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.repositories.UserRepository;
import domain.requests.posts.RequestPostTournamentInscription;
import domain.security.WordleAuthenticationManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = UriConstants.Tournaments.Inscriptions.URL)
public class TournamentPrivateInscriptionsController extends TournamentInscriptionsController {
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final WordleAuthenticationManager wordleAuthenticationManager;


    public TournamentPrivateInscriptionsController(InscriptionRepository inscriptionRepository, TournamentRepository tournamentRepository, UserRepository userRepository, WordleAuthenticationManager wordleAuthenticationManager) {
        super(inscriptionRepository);
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
        this.wordleAuthenticationManager = wordleAuthenticationManager;
    }

    @PostMapping(produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> post(@PathVariable long tournamentId, @RequestBody RequestPostTournamentInscription requestPostTournamentInscription) {
        final var tournament = this.tournamentRepository.findById(tournamentId)
                                                                   .orElseThrow(() -> new EntityNotFoundRuntimeException(Tournament.class));

        final var user = this.userRepository.findById(requestPostTournamentInscription.userId)
                                                  .orElseThrow(() -> new EntityNotFoundRuntimeException(User.class));

        tournament.validateAuthority(this.wordleAuthenticationManager.getCurrentUser());

        return this.inscribe(user, tournament);
    }
}
