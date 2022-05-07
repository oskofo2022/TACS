package domain.persistence.repositories;

import domain.persistence.entities.Match;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface MatchRepository extends JpaRepositoryImplementation<Match, Long> {
}
