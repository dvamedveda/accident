package ru.job4j.accident.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Конфигурация Hibernate и TransactionManager.
 * Первый используется для работы с базой данных.
 * Второй управляет транзакциями на основе аннотаций.
 * Параметры подключения считываются из конфига resources/db.properties.
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource("classpath:db.properties")
public class HibernateConfig {

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
    public DataSource dataSource(
            @Value("${jdbc.driver}") String driver,
            @Value("${jdbc.url}") String url,
            @Value("${jdbc.username}") String username,
            @Value("${jdbc.password}") String password
    ) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Получение объекта SessionFactory для выполнения операций с сущностями.
     * @param dialect диалект базы данных.
     * @param dataSource подключение к базе данных.
     * @return объект фабрики сессий.
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory(@Value("${hibernate.dialect}") String dialect, DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("ru.job4j.accident.entities");
        Properties config = new Properties();
        config.setProperty("hibernate.dialect", dialect);
        config.setProperty("hibernate.show_sql", "true");
        config.setProperty("format_sql", "true");
        config.setProperty("use_sql_comments", "true");
//        config.setProperty("hibernate.hbm2ddl.auto", "update");
        sessionFactory.setHibernateProperties(config);
        return sessionFactory;
    }

    /**
     * Получение менеджера транзакций Hibernate.
     * @param sessionFactory фабрика сессий Hibernate.
     * @return менеджер транзакций.
     */
    @Bean
    @Qualifier(value = "hibernateTxManager")
    public PlatformTransactionManager hibernateTxManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }
}