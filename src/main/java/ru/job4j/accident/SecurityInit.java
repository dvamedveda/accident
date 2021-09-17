package ru.job4j.accident;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Служебный класс, позволяющий томкату зарегистрировать основной фильтр SpringSecurity.
 * Который в свою очередь сможет динамически через прокси подгружать новые цепочки фильтров для запросов.
 */
public class SecurityInit extends AbstractSecurityWebApplicationInitializer {
}
