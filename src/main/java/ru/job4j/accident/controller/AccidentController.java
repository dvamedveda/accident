package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accident.entities.Accident;
import ru.job4j.accident.repository.AccidentMemRepository;

import javax.servlet.http.HttpServletRequest;

/**
 * Один из бинов-контроллеров Spring, автоматически добавляющийся в IoC-контейнер.
 * Обслуживает запросы, касающиеся инцидентов.
 */
@Controller
public class AccidentController {

    /**
     * Хранилище инцидентов.
     */
    private final AccidentMemRepository repository;

    public AccidentController(AccidentMemRepository repository) {
        this.repository = repository;
    }

    /**
     * Переход на страницу создания нового инцидента.
     *
     * @return страница для создания нового инцидента.
     */
    @GetMapping("/create")
    public String createAccidentPage() {
        return "accident/create";
    }

    /**
     * Сохранение нового инцидента.
     *
     * @param accident объект инцидента на основе данных из формы.
     * @return редирект на главную страницу.
     */
    @PostMapping("/save")
    public String saveNewAccident(@ModelAttribute Accident accident) {
        this.repository.createAccident(accident);
        return "redirect:/";
    }

    /**
     * Запрос на редактирование инцидента.
     * Здесь мы не редактируем, только ищем и добавляем к запросу инцидент.
     *
     * @param model      модель с данными об инциденте.
     * @param accidentId идентификатор редактируемого инцидента.
     * @return форвард на страницу редактирования.
     */
    @GetMapping("/edit/{accidentId}")
    public String attachAndForwardToEdit(Model model, @PathVariable("accidentId") int accidentId) {
        Accident editAccident = this.repository.getById(accidentId);
        model.addAttribute("accident", editAccident);
        return "forward:/edit";
    }

    /**
     * Редактирование инцидента.
     *
     * @param request запрос содержащий инцидент.
     * @return если в запросе есть инцидент, загружаем страницу редактирования, иначе редиректим на главную.
     */
    @GetMapping("/edit")
    public String editAccidentPage(HttpServletRequest request) {
        if (request.getAttribute("accident") != null) {
            return "accident/edit";
        }
        return "redirect:/";
    }

    /**
     * Сохранение отредактированного инцидента.
     *
     * @param accident отредактированные данные инцидента.
     * @return редирект на главную страницу.
     */
    @PostMapping("/edit")
    public String saveEditedAccident(@ModelAttribute Accident accident) {
        this.repository.updateAccident(accident);
        return "redirect:/";
    }
}