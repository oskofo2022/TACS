package domain.persistence.repositories;

import domain.persistence.entities.Tournament;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TournamentRepository extends JpaRepositoryImplementation<Tournament, UUID> {
}
