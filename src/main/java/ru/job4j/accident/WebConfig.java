package ru.job4j.accident;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Класс, содержащий конфигурацию служебных бинов для веб-приложения Spring.
 * Также выполняет сканирование пакета веб приложения для загрузки других бинов.
 */
@Configuration
@ComponentScan("ru.job4j.accident")
public class WebConfig {

    /**
     * Spring-овый бин для разрешения имени view.
     *
     * @return объект-бин для получения полного внутреннего пути к view.
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("./WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }
}