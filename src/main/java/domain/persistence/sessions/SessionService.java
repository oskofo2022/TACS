package domain.persistence.sessions;

import constants.ApplicationProperties;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SessionService {
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public SessionService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public Session session() {
        var sessionFactory = this.entityManagerFactory.unwrap(SessionFactory.class);
        return sessionFactory.getCurrentSession();
    }
}
