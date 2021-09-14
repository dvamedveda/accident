package ru.job4j.accident.entities;

import org.hibernate.annotations.Type;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.*;

/**
 * Модель данных для сущности Инцидент.
 */
@Component
@Scope("prototype")
@Entity
@Table(name = "accidents")
public class Accident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @Type(type = "text")
    private String name;

    @Column
    @Type(type = "text")
    private String text;

    @Column
    @Type(type = "text")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
    private AccidentType type;

    @Column(name = "car_number")
    @Type(type = "text")
    private String carNumber;

    @Column
    private byte[] photo;

    @Column(name = "encoded_photo")
    @Type(type = "text")
    private String encodedPhoto;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "accident_rules",
            joinColumns = {@JoinColumn(name = "accident")},
            inverseJoinColumns = {@JoinColumn(name = "rule")})
    private Set<Rule> rules = new HashSet<>();

    public static Accident of(int id, String name, String text,
                              String address, AccidentType type,
                              String carNumber, byte[] photo) {
        Accident accident = new Accident();
        accident.id = id;
        accident.name = name;
        accident.text = text;
        accident.address = address;
        accident.type = type;
        accident.carNumber = carNumber;
        accident.photo = photo;
        accident.encodedPhoto = Accident.toBase64(photo);
        return accident;
    }

    public Accident() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AccidentType getType() {
        return type;
    }

    public void setType(AccidentType type) {
        this.type = type;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getEncodedPhoto() {
        return encodedPhoto;
    }

    public void setEncodedPhoto(String encodedPhoto) {
        this.encodedPhoto = encodedPhoto;
    }

    public Set<Rule> getRules() {
        return this.rules;
    }

    public void setRules(Set<Rule> rules) {
        this.rules = rules;
    }

    public void addRule(Rule rule) {
        this.rules.add(rule);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Accident accident = (Accident) o;
        return id == accident.id
                && Objects.equals(name, accident.name)
                && Objects.equals(text, accident.text)
                && Objects.equals(address, accident.address)
                && Objects.equals(type, accident.type)
                && Objects.equals(carNumber, accident.carNumber)
                && Objects.equals(photo, accident.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, text, address, type, carNumber, photo);
    }

    public static String toBase64(byte[] data) {
        return data != null ? new String(Base64.getEncoder().encode(data)) : "";
    }
}