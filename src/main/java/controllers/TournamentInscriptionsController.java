package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
 import domain.persistence.entities.Inscription;
import domain.persistence.entities.InscriptionIdentifier;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.repositories.UserRepository;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;

@RestController
@RequestMapping(UriConstants.Tournaments.Inscriptions.URL)
public class TournamentInscriptionsController {

    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    @Autowired
    public TournamentInscriptionsController(TournamentRepository tournamentRepository, UserRepository userRepository) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(path = UriConstants.Tournaments.Inscriptions.CURRENT_USER, produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> post(@PathVariable long tournamentId) {

        // TODO: Take ID user from token session
        var user = new User();
        user = this.userRepository.findById(2L).get();

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
