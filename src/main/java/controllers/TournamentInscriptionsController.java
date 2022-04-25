package controllers;

import constants.MediaTypeConstants;
import constants.RSQLConstants;
import constants.UriConstants;
import domain.errors.runtime.EntityNotFoundRuntimeException;
import domain.persistence.entities.Inscription;
import domain.persistence.entities.InscriptionIdentifier;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.enums.Visibility;
import domain.persistence.repositories.InscriptionRepository;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.repositories.UserRepository;
import domain.security.WordleAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;

import static io.github.perplexhub.rsql.RSQLJPASupport.toSpecification;

@RestController
@RequestMapping(UriConstants.Tournaments.Public.Inscriptions.URL)
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

    @PostMapping(path = UriConstants.Tournaments.Public.Inscriptions.CURRENT_USER, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> post(@PathVariable long tournamentId) {
        final var user = this.userRepository.findById(this.wordleAuthenticationManager.getCurrentUser().getId()).get();

        final var restriction = RSQLConstants.Filters.getEqual("visibility").formatted(Visibility.PUBLIC)
                                + RSQLConstants.AND
                                + RSQLConstants.Filters.getEqual("id").formatted(tournamentId);
        final var tournament = this.tournamentRepository.findAll(toSpecification(restriction))
                                                                   .stream()
                                                                   .findFirst()
                                                                   .orElseThrow(() -> new EntityNotFoundRuntimeException(Tournament.class));

        final var inscriptionIdentifier = new InscriptionIdentifier();
        inscriptionIdentifier.setTournamentId(tournamentId);
        inscriptionIdentifier.setUserId(user.getId());

        final var inscription = new Inscription();
        inscription.setTournament(tournament);
        inscription.setUser(user);
        inscription.setIdentifier(inscriptionIdentifier);

        this.inscriptionRepository.save(inscription);

        final var location = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                             .path(UriConstants.DELIMITER + UriConstants.Users.Myself.Inscriptions.Tournaments.URL)
                                                             .query(UriConstants.Users.Myself.Inscriptions.Tournaments.Queries.ID)
                                                             .buildAndExpand(tournamentId)
                                                             .toUri();

        return ResponseEntity.created(location)
                             .build();
    }
}
