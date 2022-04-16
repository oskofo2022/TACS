package app;

import com.zaxxer.hikari.HikariDataSource;
import domain.repositories.entities.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "domain.repositories", entityManagerFactoryRef = "wordleEmf", transactionManagerRef = "wordleTm")
@EntityScan("domain.repositories.entities")
@EnableTransactionManagement
class DatabaseConfiguration {

    @Bean
    public EntityManagerFactoryBuilder builder(Environment environment) {
        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.show_sql", environment.getProperty("spring.jpa.show-sql"));
        jpaProperties.put("hibernate.format_sql", environment.getProperty("spring.jpa.properties.hibernate.format_sql"));
        jpaProperties.put("hibernate.dialect", environment.getProperty("spring.jpa.properties.hibernate.dialect"));
        return  new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), jpaProperties, null);
    }

    @Bean(name = "wordleDataSource")
    public DataSource dataSource(Environment environment) {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(environment.getRequiredProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean wordleEmf(@Qualifier("wordleDataSource") DataSource tennisDataSource, EntityManagerFactoryBuilder builder) {
        return builder.dataSource(tennisDataSource).packages(User.class).persistenceUnit("wordlePersistenceUnit").build();
    }

    @Bean
    public JpaTransactionManager wordleTm(@Qualifier("wordleEmf") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}