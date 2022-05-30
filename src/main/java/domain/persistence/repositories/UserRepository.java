package domain.persistence.repositories;

import domain.persistence.entities.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<User, UUID> {
}


