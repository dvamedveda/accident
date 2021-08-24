package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accident.entities.Accident;
import ru.job4j.accident.service.AccidentService;

import javax.servlet.http.HttpServletRequest;

/**
 * Один из бинов-контроллеров Spring, автоматически добавляющийся в IoC-контейнер.
 * Обслуживает запросы, касающиеся инцидентов.
 */
@Controller
public class AccidentController {

    /**
     * Сервис инцидентов.
     */
    private final AccidentService service;

    public AccidentController(AccidentService service) {
        this.service = service;
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
        this.service.createNewAccident(accident);
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
        Accident editAccident = this.service.getAccidentById(accidentId);
        model.addAttribute("accident", editAccident);
        return "forward:/edit";
    }


    /**
     * Запрос на редактирование инцидента (через параметр строки http-запроса)
     * Здесь мы не редактируем, только ищем и добавляем к запросу инцидент.
     *
     * @param id идентификатор инцидента из строки запроса.
     * @param model модель с данными об инциденте
     * @return возврат страницы редактирования
     */
    @GetMapping("/update")
    public String updateAccident(@RequestParam(name = "id") int id, Model model) {
        model.addAttribute("accident", this.service.getAccidentById(id));
        return "accident/edit";
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
        this.service.updateAccident(accident);
        return "redirect:/";
    }
}