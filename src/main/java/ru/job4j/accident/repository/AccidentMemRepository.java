package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.entities.Accident;

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
     * Счетчик идентификаторов инцидентов.
     */
    private AtomicInteger counter = new AtomicInteger(4);

    public AccidentMemRepository() {
        fillDummyAccidents();
    }

    /**
     * Заполнение хранилища заглушечными инцидентами.
     */
    private void fillDummyAccidents() {
        Accident accidentOne = new Accident();
        accidentOne.setId(1);
        accidentOne.setName("Dummy 1");
        accidentOne.setText("Dummy text 1");
        accidentOne.setAddress("Dummy address 1");
        store.put(accidentOne.getId(), accidentOne);
        Accident accidentTwo = new Accident();
        accidentTwo.setId(2);
        accidentTwo.setName("Dummy 2");
        accidentTwo.setText("Dummy text 2");
        accidentTwo.setAddress("Dummy address 2");
        store.put(accidentTwo.getId(), accidentTwo);
        Accident accidentThree = new Accident();
        accidentThree.setId(3);
        accidentThree.setName("Dummy 3");
        accidentThree.setText("Dummy text 3");
        accidentThree.setAddress("Dummy address 3");
        store.put(accidentThree.getId(), accidentThree);
        Accident accidentFour = new Accident();
        accidentFour.setId(4);
        accidentFour.setName("Dummy 4");
        accidentFour.setText("Dummy text 4");
        accidentFour.setAddress("Dummy address 4");
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
}