package ru.job4j.accident.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * Конфигурация SpringSecurity для приложения.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Кодировщик паролей.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Объект для подключения к базе данных.
     */
    @Autowired
    private DataSource dataSource;

    /**
     * Шифровщик/дешифровщик паролей.
     * @return объект PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Конфигурация хранилища пользователей и аутентификации.
     * @param auth билдер хранилища.
     * @throws Exception исключения при работе метода.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select u.username, a.authority "
                                + "from users u "
                                + "join authorities a on u.authority_id = a.id "
                                + "where username = ?");
    }

    /**
     * Конфигурация доступа к ресурсам приложения(авторизация),
     * настройка страницы входа, перенапаравление при входе/выходе в приложении.
     * @param http объект конфигурации обработки запросов.
     * @throws Exception исключения при работе метода.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/**").hasAnyRole("USER", "ADMIN")
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?error=true").permitAll()
                .and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
                .and().csrf().disable();
    }
}
