package domain.persistence.repositories;

import domain.persistence.entities.Game;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepositoryImplementation<Game, Long> {
}
