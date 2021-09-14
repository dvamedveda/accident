package ru.job4j.accident.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.entities.Accident;

import java.util.Optional;

/**
 * Репозиторий сущностей Инцидент.
 */
@Repository
public interface AccidentJpaRepository extends CrudRepository<Accident, Integer> {

    /**
     * Получение списка всех сущностей с присоединенными связями.
     *
     * @return перечисление инцидентов.
     */
    @Override
    @Query("select distinct a from Accident a join fetch a.type t join fetch a.rules r order by a.id asc")
    Iterable<Accident> findAll();

    /**
     * Получение инцидента по идентификатору с присоединенными связями.
     *
     * @param id идентификатор инцидента.
     * @return опшионал с инцидентом.
     */
    @Override
    @Query("select distinct a from Accident a join fetch a.type t join fetch a.rules r where a.id = :id")
    Optional<Accident> findById(@Param("id") Integer id);
}
