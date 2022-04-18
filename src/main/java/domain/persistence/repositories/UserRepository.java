package domain.persistence.repositories;

import domain.persistence.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends AbstractRepository<User> {
}


