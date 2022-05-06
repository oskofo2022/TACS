package domain.persistence.repositories;

import domain.persistence.entities.Tournament;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepositoryImplementation<Tournament, Long> {
}
