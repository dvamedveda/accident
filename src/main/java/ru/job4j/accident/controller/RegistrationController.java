package ru.job4j.accident.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accident.entities.User;
import ru.job4j.accident.exceptions.UserAlreadyExistsException;
import ru.job4j.accident.service.UserService;

/**
 * Контроллер запросов регистрации.
 */
@Controller
public class RegistrationController {

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    /**
     * Сервис для работы с пользователями.
     */
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param user пользователь из параметров запроса.
     * @return редирект на вид страницы логина.
     */
    @PostMapping("/register")
    public String save(@ModelAttribute User user) {
        String result = "false";
        try {
            userService.createUser(user);
            result = "true";
        } catch (UserAlreadyExistsException e) {
            logger.warn("Try register already existed user.");
        }
        return "redirect:/login?register=" + result;
    }

    /**
     * Получить вид страницы регистрации.
     *
     * @return вид страницы регистрации.
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }
}
