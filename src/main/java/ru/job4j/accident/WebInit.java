package ru.job4j.accident;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import ru.job4j.accident.config.HibernateConfig;
import ru.job4j.accident.config.WebConfig;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Главный класс Spring MVC приложения.
 * Наследуется от специального интерфейса, запускает приложение.
 */
public class WebInit implements WebApplicationInitializer {

    /**
     * Здесь создается IoC-контейнер, в котором регистрируется конфиг со служебным бином.
     * Затем создается фильтр, принудительно преобразующий данные в запросах в заданную кодироку.
     * Фильтр динамически регистрируется в сервлет-контексте, ему задаетсся соответствие всем запросам для обработки.
     * Затем создается специальный сервлет-диспетчер, который обслуживает все запросы, перенаправляя их
     * к определенным маппингами компонентам-контроллерам.
     * Диспетчер динамически регистрируется в сервлет-контексте, ему задается общий маппинг и первый приоритет загрузки.
     *
     * @param servletContext ServletContext приложения, созданный Tomcat.
     */
    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class, HibernateConfig.class);
        context.refresh();
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        FilterRegistration.Dynamic encoding = servletContext.addFilter("encoding", filter);
        encoding.addMappingForUrlPatterns(null, false, "/*");
        DispatcherServlet servlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}