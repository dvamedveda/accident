package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.accident.entities.Accident;
import ru.job4j.accident.entities.AccidentType;
import ru.job4j.accident.entities.Rule;
import ru.job4j.accident.repository.AccidentJdbcRepository;

import java.io.IOException;
import java.util.*;

/**
 * Сервис для работы с инцидентами.
 */
@Service
@Transactional
public class AccidentService {

    /**
     * Хранилище инцидентов.
     */
    private AccidentJdbcRepository repository;

    public AccidentService(AccidentJdbcRepository repository) {
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
    public Accident createNewAccident(Accident accident, MultipartFile file, String[] ruleIds) {
        accident.setPhoto(file.getSize() > 0 ? this.getPhotoBytes(file) : null);
        accident.setEncodedPhoto(file.getSize() > 0
                ? Accident.toBase64(this.getPhotoBytes(file)) : Accident.toBase64(null));
        this.fillAccidentRules(accident, ruleIds);
        return this.repository.createAccident(this.fillAccidentTypeProperties(accident));
    }

    /**
     * Обновление существуеющего инцидента.
     *
     * @param accident обновляемый инцидент.
     */
    public void updateAccident(Accident accident, MultipartFile file, String[] ruleIds) {
        byte[] currentPhoto = this.repository.getById(accident.getId()).getPhoto();
        accident.setPhoto(file.getSize() > 0 ? this.getPhotoBytes(file) : currentPhoto);
        accident.setEncodedPhoto(file.getSize() > 0
                ? Accident.toBase64(this.getPhotoBytes(file)) : Accident.toBase64(currentPhoto));
        this.fillAccidentRules(accident, ruleIds);
        this.repository.updateAccident(this.fillAccidentTypeProperties(accident));
    }

    /**
     * Получить все возможные типы инцидентов.
     *
     * @return список возможных инцидентов.
     */
    public List<AccidentType> possibleTypes() {
        return this.repository.getAccidentTypes();
    }

    /**
     * Заполняем поля выбранного типа инцидента в объекте инцидента.
     * Так как он приходит со слоя контроллера только с проинициализированным идентификатором.
     * @param accident принятый объект инцидента.
     * @return объект инцидента с заполненными полями.
     */
    private Accident fillAccidentTypeProperties(Accident accident) {
        int chosenTypeId = accident.getType().getId();
        AccidentType chosenType = this.repository.getAccidentTypeById(chosenTypeId);
        accident.getType().setName(chosenType.getName());
        return accident;
    }

    /**
     * Получение данных фото из загруженного файла.
     * @param file загруженный файл.
     * @return массив байт данных из фото.
     */
    private byte[] getPhotoBytes(MultipartFile file) {
        byte[] result = null;
        try {
            result = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Получение всех статей из репозитория.
     * @return множество всех статей.
     */
    public Set<Rule> getAllRules() {
        return this.repository.getAllRules();
    }

    /**
     * Получить объект статьи по ее индентификатору.
     * @param id идентификатор статьи.
     * @return объект статьи.
     */
    public Rule getRuleById(int id) {
        return this.repository.getRuleById(id);
    }

    /**
     * Заполнение списка статей инцидента на основе переданного списка идентификаторов статей.
     * @param accident инцидент, в котором требуется заполнить статьи.
     * @param ids переданный список статей.
     */
    private void fillAccidentRules(Accident accident, String[] ids) {
        Arrays.stream(ids).map(Integer::parseInt).map(this::getRuleById).forEach(accident::addRule);
    }
}