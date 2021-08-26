package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.entities.Accident;
import ru.job4j.accident.entities.AccidentType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Хранилище инцидентов, использующее для хранения память.
 */
@Repository
public class AccidentMemRepository {

    /**
     * Хранилище инцидентов.
     */
    private Map<Integer, Accident> store = new HashMap<>();

    /**
     * Хранилище типов инцидентов.
     */
    private Map<Integer, AccidentType> types = new HashMap<>();

    /**
     * Счетчик идентификаторов инцидентов.
     */
    private AtomicInteger counter = new AtomicInteger(4);

    public AccidentMemRepository() {
        fillTypes();
        fillDummyAccidents();
    }

    /**
     * Заполнить хранилище типов инцидентов.
     */
    private void fillTypes() {
        this.types.put(1, AccidentType.of(1, "Столкновение машины с другой машиной"));
        this.types.put(2, AccidentType.of(2, "Столкновение машины с пешеходом"));
        this.types.put(3, AccidentType.of(3, "Столкновение машины с неподвижным объектом"));
        this.types.put(4, AccidentType.of(4, "Столкновение машины с легким транспортом"));
        this.types.put(5, AccidentType.of(5, "Столкновение легкого транспорта с другим легким транспортом"));
        this.types.put(6, AccidentType.of(6, "Столкновение легкого транспорта с пешеходом"));
        this.types.put(7, AccidentType.of(7, "Столкновение легкого транспорта с неподвижным объектом"));
        this.types.put(8, AccidentType.of(8, "Легкий транспорт аварийно съехал с дороги"));
        this.types.put(9, AccidentType.of(9, "Машина аварийно съехала с дороги"));
    }

    /**
     * Заполнение хранилища заглушечными инцидентами.
     */
    private void fillDummyAccidents() {
        Accident accidentOne = Accident.of(1, "Dummy 1", "Dummy text 1", "Dummy address 1",
                this.getAccidentTypeById(2), "123", "Dummy photo 1".getBytes());
        store.put(accidentOne.getId(), accidentOne);
        Accident accidentTwo = Accident.of(2, "Dummy 2", "Dummy text 2", "Dummy address 2",
                this.getAccidentTypeById(4), "234", "Dummy photo 2".getBytes());
        store.put(accidentTwo.getId(), accidentTwo);
        Accident accidentThree = Accident.of(3, "Dummy 3", "Dummy text 3", "Dummy address 3",
                this.getAccidentTypeById(6), "345", "Dummy photo 3".getBytes());
        store.put(accidentThree.getId(), accidentThree);
        Accident accidentFour = Accident.of(4, "Dummy 4", "Dummy text 4", "Dummy address 4",
                this.getAccidentTypeById(8), "456", "Dummy photo 4".getBytes());
        store.put(accidentFour.getId(), accidentFour);
    }

    /**
     * Получение коллекции всех инцидентов.
     *
     * @return коллекция всех инцидентов.
     */
    public Collection<Accident> getAllAccidents() {
        return store.values();
    }

    /**
     * Создание нового инцидента в хранилище.
     *
     * @param accident объект нового инцидента.
     * @return сохраненный инцидент.
     */
    public Accident createAccident(Accident accident) {
        accident.setId(this.counter.incrementAndGet());
        this.store.put(accident.getId(), accident);
        return accident;
    }

    /**
     * Получение инцидента по его идентификатору.
     *
     * @param id идентификатор инцидента.
     * @return объект инцидента.
     */
    public Accident getById(int id) {
        return this.store.get(id);
    }

    /**
     * Обновление существующего инцидента.
     *
     * @param accident объект инцидента.
     */
    public void updateAccident(Accident accident) {
        if (this.store.containsKey(accident.getId())) {
            this.store.remove(accident.getId());
            this.store.put(accident.getId(), accident);
        }
    }

    /**
     * Получение типа инцидента по идентификатору.
     *
     * @param id идентификатор типа инцидента.
     * @return тип инцидента.
     */
    public AccidentType getAccidentTypeById(int id) {
        return this.types.get(id);
    }

    /**
     * Получить список всех типов инцидентов.
     *
     * @return список инцидентов.
     */
    public List<AccidentType> getAccidentTypes() {
        return new ArrayList<>(this.types.values());
    }
}