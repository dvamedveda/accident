package ru.job4j.accident.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * Сущность Пользователь.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * Идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Логин пользователя.
     */
    @Column
    private String username;

    /**
     * Пароль пользователя.
     * Хранится в закодированном виде.
     */
    @Column
    private String password;

    /**
     * Отметка об активности пользователя.
     */
    @Column
    private boolean enabled;

    /**
     * Роль пользователя.
     */
    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && enabled == user.enabled
                && authority == user.authority
                && Objects.equals(username, user.username)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, enabled, authority);
    }
}
