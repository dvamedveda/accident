package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Один из бинов Spring, автоматически добавляющийся в IoC-контейнер.
 * Обслуживает подходящие запросы к веб приложению.
 */
@Controller
public class IndexController {

    /**
     * Используется для выдачи view http-запросам типа GET по адресу "/" (корень веб-контекста)
     *
     * @param model объект запроса
     * @return наименование view.
     */
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
}