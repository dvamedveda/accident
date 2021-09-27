package ru.job4j.accident.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
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
        String result = "true";
        try {
            userService.createUser(user);
        } catch (UserAlreadyExistsException e) {
            result = "false";
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
