package domain.persistence.repositories;

import domain.persistence.entities.Inscription;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface InscriptionRepository extends JpaRepositoryImplementation<Inscription, Long> {
}
