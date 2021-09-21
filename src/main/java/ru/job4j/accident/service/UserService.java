package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.entities.Authority;
import ru.job4j.accident.entities.User;
import ru.job4j.accident.repository.AuthorityRepository;
import ru.job4j.accident.repository.UserRepository;

/**
 * Сервис для работы с пользователями.
 */
@Service
public class UserService {

    /**
     * Хранилище пользователей.
     */
    private final UserRepository userRepository;

    /**
     * Хранилище ролей.
     */
    private final AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    /**
     * Получить дефолтную роль для регистрации.
     *
     * @return объект дефолтной роли.
     */
    public Authority getDefaultAuthority() {
        return this.authorityRepository.findByAuthority("ROLE_USER");
    }

    /**
     * Сохранить нового пользователя в бд.
     *
     * @param user объект пользователя.
     */
    public void createUser(User user) {
        this.userRepository.save(user);
    }
}
