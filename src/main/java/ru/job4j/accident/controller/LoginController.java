package ru.job4j.accident.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Контроллер для обработки входов и выходов пользователей.
 */
@Controller
public class LoginController {

    /**
     * Вход пользователя в приложение.
     * @param error сообщение об ошибке логина.
     * @param logout сообщение о логауте.
     * @param model модель с данными для страницы.
     * @return вид для входа в приложение.
     */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String message = null;
        if (error != null) {
            message = "Username or password incorrect.";
        }
        if (logout != null) {
            message = "You have been logged out";
        }
        model.addAttribute("message", message);
        return "login";
    }

    /**
     * Выход пользователя из приложения.
     * @param request объект http-запроса.
     * @param response объект http-ответа.
     * @return редирект на вид для входа в приложение.
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout=true";
    }
}
