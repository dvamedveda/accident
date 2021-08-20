package ru.job4j.accident.entities;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
        String address = "Addres for test";
        newAccident.setId(111);
        newAccident.setText(text);
        newAccident.setName(name);
        newAccident.setAddress(address);
        Assert.assertThat(newAccident.getId(), is(111));
        Assert.assertEquals(newAccident.getName(), name);
        Assert.assertEquals(newAccident.getText(), text);
        Assert.assertEquals(newAccident.getAddress(), address);
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
        String address = "Addres for test";
        accidentOne.setId(111);
        accidentOne.setText(text);
        accidentOne.setName(name);
        accidentOne.setAddress(address);
        accidentTwo.setId(111);
        accidentTwo.setText(text);
        accidentTwo.setName(name);
        accidentTwo.setAddress(address);
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
        accidentOne.setId(111);
        accidentOne.setText(text);
        accidentOne.setName(name);
        accidentOne.setAddress(address);
        accidentTwo.setId(222);
        accidentTwo.setText(text);
        accidentTwo.setName(name);
        accidentTwo.setAddress(address);
        Assert.assertNotEquals(accidentOne, accidentTwo);
        Assert.assertNotEquals(accidentOne.hashCode(), accidentTwo.hashCode());
    }
}