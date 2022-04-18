package domain.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepository<TEntity> extends JpaRepository<TEntity, Long>, JpaSpecificationExecutor<TEntity> {
}
