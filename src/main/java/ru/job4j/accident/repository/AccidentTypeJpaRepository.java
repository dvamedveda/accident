package ru.job4j.accident.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.entities.AccidentType;

/**
 * Репозиторий типов инцидентов.
 */
@Repository
public interface AccidentTypeJpaRepository extends CrudRepository<AccidentType, Integer> {
}
