package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

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
        model.addAttribute("users", getUserList());
        return "index";
    }

    /**
     * Список неких пользователей для передачи на страницу.
     * @return список пользователей.
     */
    private List<String> getUserList() {
        List<String> users = new ArrayList<>();
        users.add("Petr Arsentev");
        users.add("Boris Savelev");
        users.add("Ivan Ivanov");
        users.add("Sidor Sidorov");
        users.add("Fedor Fedorov");
        return users;
    }
}