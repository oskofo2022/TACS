package domain.persistence.repositories;

import domain.persistence.entities.Game;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends AbstractRepository<Game> {
}
