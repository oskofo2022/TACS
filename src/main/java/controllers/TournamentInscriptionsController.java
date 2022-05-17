package controllers;

import constants.UriConstants;
import domain.errors.runtime.DuplicateEntityFoundRuntimeException;
import domain.persistence.entities.Inscription;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.queries.SpecificationBuilder;
import domain.persistence.repositories.InscriptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class TournamentInscriptionsController {

    private final InscriptionRepository inscriptionRepository;

    public TournamentInscriptionsController(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    protected ResponseEntity<Void> inscribe(User user, Tournament tournament) {
        this.validateDuplicate(user, tournament);

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

    private void validateDuplicate(User user, Tournament tournament) {

        final var specificationBuilder = new SpecificationBuilder();

        specificationBuilder.andEqual("identifier.userId", user.getId())
                            .andEqual("identifier.tournamentId", tournament.getId());

        this.inscriptionRepository.findAll(specificationBuilder.build())
                                  .stream()
                                  .findAny()
                                  .ifPresent((i) -> {
                                    throw new DuplicateEntityFoundRuntimeException(Inscription.class);
                                  });
    }
}
