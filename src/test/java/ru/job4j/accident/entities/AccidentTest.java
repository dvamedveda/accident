package ru.job4j.accident.entities;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;

/**
 * Тесты класса Accident.
 */
public class AccidentTest {

    /**
     * Spring-контейнер для получения объектов.
     */
    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    @BeforeClass
    public static void setUp() {
        context.scan("ru.job4j.accident");
        context.refresh();
    }

    /**
     * Проверяется заполнение инцидента данными.
     */
    @Test
    public void whenFillAccidentThenCorrect() {
        Accident newAccident = context.getBean(Accident.class);
        String text = "Text for test";
        String name = "Name for test";
        String address = "Address for test";
        String carNumber = "123qwe";
        AccidentType type = AccidentType.of(1, "Test accident type");
        byte[] photo = "test photo".getBytes();
        String encodedPhoto = "encoded photo";
        newAccident.setId(111);
        newAccident.setText(text);
        newAccident.setName(name);
        newAccident.setAddress(address);
        newAccident.setCarNumber(carNumber);
        newAccident.setPhoto(photo);
        newAccident.setType(type);
        newAccident.setEncodedPhoto(encodedPhoto);
        Assert.assertThat(newAccident.getId(), is(111));
        Assert.assertEquals(newAccident.getName(), name);
        Assert.assertEquals(newAccident.getText(), text);
        Assert.assertEquals(newAccident.getAddress(), address);
        Assert.assertEquals(newAccident.getCarNumber(), carNumber);
        Assert.assertEquals(Arrays.toString(newAccident.getPhoto()), Arrays.toString(photo));
        Assert.assertEquals(newAccident.getEncodedPhoto(), encodedPhoto);
        Assert.assertEquals(newAccident.getType(), type);
    }

    /**
     * Проверяется, что одинаковые инциденты эквивалентны.
     */
    @Test
    public void whenSameThenEquals() {
        Accident accidentOne = context.getBean(Accident.class);
        Accident accidentTwo = context.getBean(Accident.class);
        String text = "Text for test";
        String name = "Name for test";
        String address = "Address for test";
        String carNumber = "123qwe";
        AccidentType type = AccidentType.of(1, "Test accident type");
        byte[] photo = "test photo".getBytes();
        String encodedPhoto = "encoded photo";
        accidentOne.setId(111);
        accidentOne.setText(text);
        accidentOne.setName(name);
        accidentOne.setType(type);
        accidentOne.setPhoto(photo);
        accidentOne.setCarNumber(carNumber);
        accidentOne.setAddress(address);
        accidentOne.setEncodedPhoto(encodedPhoto);
        accidentTwo.setId(111);
        accidentTwo.setText(text);
        accidentTwo.setName(name);
        accidentTwo.setAddress(address);
        accidentTwo.setType(type);
        accidentTwo.setPhoto(photo);
        accidentTwo.setCarNumber(carNumber);
        accidentTwo.setEncodedPhoto(encodedPhoto);
        Assert.assertEquals(accidentOne, accidentTwo);
        Assert.assertEquals(accidentOne.hashCode(), accidentTwo.hashCode());
    }

    /**
     * Проверяется, что разные инциденты не эквивалентны.
     */
    @Test
    public void whenDiffersThenNotEquals() {
        Accident accidentOne = context.getBean(Accident.class);
        Accident accidentTwo = context.getBean(Accident.class);
        String text = "Text for test";
        String name = "Name for test";
        String address = "Addres for test";
        String carNumber = "123qwe";
        AccidentType type = AccidentType.of(1, "Test accident type");
        byte[] photo = "test photo".getBytes();
        String encodedPhoto = "encoded photo";
        accidentOne.setId(111);
        accidentOne.setText(text);
        accidentOne.setName(name);
        accidentOne.setAddress(address);
        accidentOne.setType(type);
        accidentOne.setPhoto(photo);
        accidentOne.setCarNumber(carNumber);
        accidentOne.setEncodedPhoto(encodedPhoto);
        accidentTwo.setId(222);
        accidentTwo.setText(text);
        accidentTwo.setName(name);
        accidentTwo.setAddress(address);
        accidentTwo.setType(type);
        accidentTwo.setPhoto(photo);
        accidentTwo.setCarNumber(carNumber);
        accidentTwo.setEncodedPhoto(encodedPhoto);
        Assert.assertNotEquals(accidentOne, accidentTwo);
        Assert.assertNotEquals(accidentOne.hashCode(), accidentTwo.hashCode());
    }
}