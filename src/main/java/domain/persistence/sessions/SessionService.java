package domain.persistence.sessions;

import constants.ApplicationProperties;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionService {

    @Value(ApplicationProperties.Spring.Datasource.Arguments.URL)
    private String connectionUrl;

    @Value(ApplicationProperties.Spring.Datasource.Arguments.DRIVER_CLASS_NAME)
    private String driverClassName;

    @Value(ApplicationProperties.Spring.Datasource.Arguments.USERNAME)
    private String username;

    @Value(ApplicationProperties.Spring.Datasource.Arguments.PASSWORD)
    private String password;

    @Value(ApplicationProperties.Spring.JPA.Properties.Hibernate.Arguments.DIALECT)
    private String dialect;

    @Value(ApplicationProperties.Spring.JPA.Arguments.GENERATE_DDL)
    private String generateDDL;

    @Value(ApplicationProperties.Spring.JPA.Arguments.OPEN_IN_VIEW)
    private String openInView;

    @Value(ApplicationProperties.Spring.JPA.Hibernate.Arguments.DDL_AUTO)
    private String dDLAuto;

    @Bean
    public Session session() {
        Map<String, String> settings = new HashMap<>();
        settings.put("connection.driver_class_name", this.driverClassName);
        settings.put("hibernate.dialect", this.dialect);
        settings.put("hibernate.connection.url", this.connectionUrl);
        settings.put("hibernate.connection.username", this.username);
        settings.put("hibernate.connection.password", this.password);
        settings.put("hibernate.current_session_context_class", "thread");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.format_sql", "true");
        settings.put("hibernate.generate-ddl", this.generateDDL);
        settings.put("hibernate.open-in-view", this.openInView);
        settings.put("hibernate.ddl-auto", this.dDLAuto);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settings)
                                                                              .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Metadata metadata = metadataSources.buildMetadata();

        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        return sessionFactory.getCurrentSession();
    }
}
