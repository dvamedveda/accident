package ru.job4j.accident.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accident.entities.User;

/**
 * Репозиторий для работы с пользователями.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
}
