package ru.job4j.accident.repository;

import ru.job4j.accident.entities.Accident;
import ru.job4j.accident.entities.AccidentType;
import ru.job4j.accident.entities.Rule;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Интерфейс репозитория с данными.
 */
public interface AccidentRepository {

    /**
     * Получить все инциденты.
     *
     * @return коллекция инцидентов.
     */
    Collection<Accident> getAllAccidents();

    /**
     * Создание нового инцидента.
     *
     * @param accident инцидент без идентификатора.
     * @return инцидент с назначенным идентификатором.
     */
    Accident createAccident(Accident accident);

    /**
     * Получение инцидента по идентификатору.
     *
     * @param id идентификатор.
     * @return объект инцидента.
     */
    Accident getById(int id);

    /**
     * Обновить существующий инцидент.
     *
     * @param accident объект инцидента, содержащий новые данные.
     */
    void updateAccident(Accident accident);

    /**
     * Получение типа инцидента по идентификатору.
     *
     * @param id идентификатор типа инцидента.
     * @return объект типа инцидента.
     */
    AccidentType getAccidentTypeById(int id);

    /**
     * Получить все типы инцидентов.
     *
     * @return список типов инцидентов.
     */
    List<AccidentType> getAccidentTypes();

    /**
     * Получить статью по идентификатору.
     *
     * @param id идентификатор статьи.
     * @return объект статьи.
     */
    Rule getRuleById(int id);

    /**
     * Получить список всех статей.
     *
     * @return множество статей.
     */
    Set<Rule> getAllRules();
}
