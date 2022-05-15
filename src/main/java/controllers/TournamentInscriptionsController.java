package controllers;

import constants.UriConstants;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.repositories.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class TournamentInscriptionsController {

    private final InscriptionRepository inscriptionRepository;

    public TournamentInscriptionsController(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    protected ResponseEntity<Void> inscribe(User user, Tournament tournament) {
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
