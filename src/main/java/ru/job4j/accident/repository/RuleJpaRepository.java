package ru.job4j.accident.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accident.entities.Rule;

/**
 * Репозиторий статей для инцидентов.
 */
public interface RuleJpaRepository extends CrudRepository<Rule, Integer> {
}
