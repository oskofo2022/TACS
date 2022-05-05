package domain.persistence.repositories;

import domain.persistence.entities.Inscription;
import domain.persistence.entities.InscriptionIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InscriptionRepository extends AbstractRepository<Inscription> {
}
