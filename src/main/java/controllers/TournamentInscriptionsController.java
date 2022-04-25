package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
 import domain.persistence.entities.Inscription;
import domain.persistence.entities.InscriptionIdentifier;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.repositories.UserRepository;
import domain.security.WordleAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(UriConstants.Tournaments.Inscriptions.URL)
public class TournamentInscriptionsController {

    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final WordleAuthenticationManager wordleAuthenticationManager;
    @Autowired
    public TournamentInscriptionsController(TournamentRepository tournamentRepository, UserRepository userRepository, WordleAuthenticationManager wordleAuthenticationManager) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
        this.wordleAuthenticationManager = wordleAuthenticationManager;
    }

    @PostMapping(path = UriConstants.Tournaments.Inscriptions.CURRENT_USER, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> post(@PathVariable long tournamentId) {

        var user = new User();
        user = this.userRepository.findById(2L).get();

        /*
        final var wordleUser = this.wordleAuthenticationManager.getCurrentUser();

        var user = new User();
        user = this.userRepository.findById(wordleUser.getId()).get();
         */

        var tournament = new Tournament();
        tournament = this.tournamentRepository.findById(tournamentId).get();

        var inscriptionIdentifier = new InscriptionIdentifier();
        inscriptionIdentifier.setTournamentId(tournamentId);
        inscriptionIdentifier.setUserId(user.getId());

        var inscription = new Inscription();
        inscription.setTournament(tournament);
        inscription.setUser(user);
        inscription.setIdentifier(inscriptionIdentifier);

        var inscriptions = tournament.getInscriptions();
        inscriptions.add(inscription);
        tournament.setInscriptions(inscriptions);

        this.tournamentRepository.save(tournament);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path(UriConstants.DELIMITER + UriConstants.Users.Myself.Inscriptions.Tournaments.URL)
                                                                           .query(UriConstants.Users.Myself.Inscriptions.Tournaments.Queries.ID)
                                                                           .buildAndExpand(tournamentId)
                                                                           .toUri();

        return ResponseEntity.created(location)
                             .build();
    }
}
