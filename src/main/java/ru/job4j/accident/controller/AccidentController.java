package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public String createAccidentPage(Model model) {
        model.addAttribute("types", this.service.possibleTypes());
        model.addAttribute("rules", this.service.getAllRules());
        return "accident/create";
    }

    /**
     * Сохранение нового инцидента.
     *
     * @param accident объект инцидента на основе данных из формы.
     * @return редирект на главную страницу.
     */
    @PostMapping("/save")
    public String saveNewAccident(@RequestParam("accidentPhoto") MultipartFile file,
                                  @ModelAttribute Accident accident,
                                  HttpServletRequest request) {
        String[] ruleIds = request.getParameterValues("rIds");
        this.service.createNewAccident(accident, file, ruleIds);
        return "redirect:/";
    }

    /**
     * Запрос на редактирование инцидента (через параметр строки http-запроса)
     *
     * @param id    идентификатор инцидента из строки запроса.
     * @param model модель с данными об инциденте
     * @return возврат страницы редактирования
     */
    @GetMapping("/update")
    public String updateAccident(@RequestParam(name = "id") int id, Model model) {
        model.addAttribute("accident", this.service.getAccidentById(id));
        model.addAttribute("types", this.service.possibleTypes());
        model.addAttribute("rules", this.service.getAllRules());
        return "accident/edit";
    }

    /**
     * Сохранение отредактированного инцидента.
     *
     * @param accident отредактированные данные инцидента.
     * @return редирект на главную страницу.
     */
    @PostMapping("/edit")
    public String saveEditedAccident(@RequestParam("accidentPhoto") MultipartFile file,
                                     @ModelAttribute Accident accident,
                                     HttpServletRequest request) {
        String[] ruleIds = request.getParameterValues("rIds");
        this.service.updateAccident(accident, file, ruleIds);
        return "redirect:/";
    }
}