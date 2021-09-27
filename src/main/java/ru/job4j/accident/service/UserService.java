package ru.job4j.accident.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.accident.entities.Authority;
import ru.job4j.accident.entities.User;
import ru.job4j.accident.exceptions.UserAlreadyExistsException;
import ru.job4j.accident.repository.AuthorityRepository;
import ru.job4j.accident.repository.UserRepository;

/**
 * Сервис для работы с пользователями.
 */
@Service
public class UserService {

    /**
     * Кодировщик паролей.
     */
    private final PasswordEncoder encoder;

    /**
     * Хранилище пользователей.
     */
    private final UserRepository userRepository;

    /**
     * Хранилище ролей.
     */
    private final AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.encoder = encoder;
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
     * @param user объект пользователя.
     * @throws UserAlreadyExistsException в случае, если пользователь с таким именем уже существует.
     */
    public void createUser(User user) throws UserAlreadyExistsException {
        if (this.userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        } else {
            user.setEnabled(true);
            user.setPassword(encoder.encode(user.getPassword()));
            user.setAuthority(this.getDefaultAuthority());
            this.userRepository.save(user);
        }
    }
}
