package ru.job4j.accident.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Конфигурация Spring Data JPA и TransactionManager.
 * Первый используется для работы с базой данных.
 * Второй управляет транзакциями на основе аннотаций.
 * Параметры подключения считываются из конфига resources/db.properties.
 */
@Configuration
@PropertySource("classpath:/db.properties")
@EnableTransactionManagement
@EnableJpaRepositories("ru.job4j.accident.repository")
public class SpringDataConfig {

    /**
     * Создание источника данных (на основе пула Apache DBCP)
     *
     * @param driver   драйвер для подключения к бд.
     * @param url      адрес расположения бд.
     * @param username пользователь.
     * @param password пароль.
     * @return источник данных для получения подключения к бд.
     */
    @Bean
    public DataSource dataSource(@Value("${jdbc.driver}") String driver,
                                 @Value("${jdbc.url}") String url,
                                 @Value("${jdbc.username}") String username,
                                 @Value("${jdbc.password}") String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Создание объекта фабрики entity-manager-ов для работы с сущностями.
     *
     * @param dataSource источник данных для работы с бд.
     * @return экземпляр фабрики entity-manager-ов.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Value("${hibernate.dialect}") String dialect,
                                                                       DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ru.job4j.accident");
        factory.setDataSource(dataSource);
        Properties config = new Properties();
        config.setProperty("hibernate.dialect", dialect);
        factory.setJpaProperties(config);
        return factory;
    }

    /**
     * Создание менеджера транзакций для управления транзакциями.
     *
     * @param entityManagerFactory фабрика entity-manager-ов.
     * @return объект менеджера транзакций.
     */
    @Bean
    @Qualifier("jpaTxManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
