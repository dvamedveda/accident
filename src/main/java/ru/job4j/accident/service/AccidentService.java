package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.entities.Accident;
import ru.job4j.accident.repository.AccidentMemRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для работы с инцидентами.
 */
@Service
public class AccidentService {

    /**
     * Хранилище инцидентов.
     */
    private AccidentMemRepository repository;

    public AccidentService(AccidentMemRepository repository) {
        this.repository = repository;
    }

    /**
     * Получение всех инцидентов в виде списка.
     *
     * @return список всех инцидентов.
     */
    public List<Accident> getAllAccident() {
        return new ArrayList<>(this.repository.getAllAccidents());
    }

    /**
     * Получение инцидента по идентификатору.
     *
     * @param id идентификатор инцидента.
     * @return объект инцидента.
     */
    public Accident getAccidentById(int id) {
        return this.repository.getById(id);
    }

    /**
     * Создание нового инцидента.
     *
     * @param accident создаваемый инцидент.
     * @return созданный инцидент.
     */
    public Accident createNewAccident(Accident accident) {
        return this.repository.createAccident(accident);
    }

    /**
     * Обновление существуеющего инцидента.
     *
     * @param accident обновляемый инцидент.
     */
    public void updateAccident(Accident accident) {
        this.repository.updateAccident(accident);
    }
}