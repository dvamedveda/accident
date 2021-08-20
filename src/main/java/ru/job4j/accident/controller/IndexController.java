package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.service.AccidentService;

/**
 * Один из бинов Spring, автоматически добавляющийся в IoC-контейнер.
 * Обслуживает подходящие запросы к веб приложению.
 */
@Controller
public class IndexController {

    private AccidentService accidentService;

    public IndexController(AccidentService service) {
        this.accidentService = service;
    }

    /**
     * Используется для выдачи view http-запросам типа GET по адресу "/" (корень веб-контекста)
     *
     * @param model объект запроса
     * @return наименование view.
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("accidents", this.accidentService.getAllAccident());
        return "index";
    }
}