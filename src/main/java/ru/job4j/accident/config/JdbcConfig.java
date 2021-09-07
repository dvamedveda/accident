package ru.job4j.accident.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Конфигурация JDBCTemplate и TransactionManager.
 * Первый используется для доступа к базе данных.
 * Второй управляет транзакциями на основе аннотаций.
 * Параметры подключения считываются из конфига resources/db.properties.
 */
@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement(proxyTargetClass = true)
public class JdbcConfig {

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
            @Value("${jdbc.password}") String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Создание JDBCTemplate для доступа к данным в бд.
     *
     * @param dataSource источник данных.
     * @return jdbc темплейт, через который можно выполнять запросы к бд.
     */
    @Bean
    public JdbcTemplate jdbcRepository(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Создание менеджера транзакций, используемого в репозитории для управления транзакциями.
     *
     * @param dataSource используемый источник данных.
     * @return объект менеджера транзакций.
     */
    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}