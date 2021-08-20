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

    private AccidentMemRepository repository;

    public AccidentService(AccidentMemRepository repository) {
        this.repository = repository;
    }

    /**
     * Получение всех инцидентов в виде списка.
     * @return список всех инцидентов.
     */
    public List<Accident> getAllAccident() {
        return new ArrayList<>(this.repository.getAllAccidents());
    }
}