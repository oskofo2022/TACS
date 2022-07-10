package controllers;

import constants.UriConstants;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.repositories.TournamentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.InvocationTargetException;

public abstract class TournamentInscriptionsController {

    protected final TournamentRepository tournamentRepository;

    public TournamentInscriptionsController(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    protected ResponseEntity<Void> inscribe(User user, Tournament tournament) {

        tournament.inscribe(user);

        this.tournamentRepository.save(tournament);

        final var location = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                             .path(UriConstants.DELIMITER + UriConstants.Users.Myself.Inscriptions.Tournaments.URL)
                                                             .query(UriConstants.Users.Myself.Inscriptions.Tournaments.Queries.ID)
                                                             .buildAndExpand(tournament.getId())
                                                             .toUri();

        return ResponseEntity.created(location)
                             .build();
    }
}
