package domain.persistence.repositories;

import domain.persistence.entities.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<User, Long> {
}


