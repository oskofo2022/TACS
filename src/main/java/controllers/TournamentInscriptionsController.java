package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.errors.runtime.EntityNotFoundRuntimeException;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.Visibility;
import domain.persistence.repositories.InscriptionRepository;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.repositories.UserRepository;
import domain.requests.posts.RequestPostTournamentInscription;
import domain.security.WordleAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class TournamentInscriptionsController {

    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final WordleAuthenticationManager wordleAuthenticationManager;
    private final InscriptionRepository inscriptionRepository;

    @Autowired
    public TournamentInscriptionsController(TournamentRepository tournamentRepository, UserRepository userRepository, WordleAuthenticationManager wordleAuthenticationManager, InscriptionRepository inscriptionRepository) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
        this.wordleAuthenticationManager = wordleAuthenticationManager;
        this.inscriptionRepository = inscriptionRepository;
    }

    @PostMapping(path =  UriConstants.Tournaments.Public.Inscriptions.URL + UriConstants.Tournaments.Public.Inscriptions.CURRENT_USER, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> post(@PathVariable long tournamentId) {
        final var user = this.userRepository.findById(this.wordleAuthenticationManager.getCurrentUser().getId())
                                                  .get();

        final var tournament = this.tournamentRepository.findById(tournamentId)
                                                                   .filter(t -> t.getVisibility() == Visibility.PUBLIC)
                                                                   .orElseThrow(() -> new EntityNotFoundRuntimeException(Tournament.class));

        return this.inscribe(user, tournament);
    }



    @PostMapping(path = UriConstants.Tournaments.Inscriptions.URL, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> post(@PathVariable long tournamentId, @RequestBody RequestPostTournamentInscription requestPostTournamentInscription) {
        final var user = this.userRepository.findById(requestPostTournamentInscription.userId)
                                                  .orElseThrow(() -> new EntityNotFoundRuntimeException(User.class));

        final var tournament = this.tournamentRepository.findById(tournamentId)
                                                                   .orElseThrow(() -> new EntityNotFoundRuntimeException(Tournament.class));

        tournament.validateAuthority(this.wordleAuthenticationManager.getCurrentUser());

        return this.inscribe(user, tournament);
    }

    private ResponseEntity<Void> inscribe(User user, Tournament tournament) {
        //TODO: Validate duplicate inscription

        final var inscription = tournament.inscribe(user);

        this.inscriptionRepository.save(inscription);

        final var location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(UriConstants.DELIMITER + UriConstants.Users.Myself.Inscriptions.Tournaments.URL)
                .query(UriConstants.Users.Myself.Inscriptions.Tournaments.Queries.ID)
                .buildAndExpand(tournament.getId())
                .toUri();

        return ResponseEntity.created(location)
                .build();
    }
}
