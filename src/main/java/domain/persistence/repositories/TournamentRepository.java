package domain.persistence.repositories;

import domain.persistence.entities.Tournament;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends AbstractRepository<Tournament> {
}
