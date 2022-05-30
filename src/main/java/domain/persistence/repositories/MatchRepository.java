package domain.persistence.repositories;

import domain.persistence.entities.Match;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.UUID;

public interface MatchRepository extends JpaRepositoryImplementation<Match, UUID> {
}
