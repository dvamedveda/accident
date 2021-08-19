package ru.job4j.accident;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Главный класс Spring MVC приложения.
 * Наследуется от специального интерфейса, запускает приложение.
 */
public class WebInit implements WebApplicationInitializer {

    /**
     * Здесь создается IoC-контейнер, в котором регистрируется конфиг со служебным бином.
     * Затем создается специальный сервлет-диспетчер, который обслуживает все запросы, перенаправляя их
     * к определенным маппингами компонентам-контроллерам.
     * Диспетчер динамически регистрируется в сервлет-контексте, ему задается общий маппинг и первый приоритет загрузки.
     *
     * @param servletContext ServletContext приложения, созданный Tomcat.
     */
    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);
        context.refresh();
        DispatcherServlet servlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}