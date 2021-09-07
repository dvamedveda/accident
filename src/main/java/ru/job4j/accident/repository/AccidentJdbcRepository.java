package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.entities.Accident;
import ru.job4j.accident.entities.AccidentType;
import ru.job4j.accident.entities.Rule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@inheritDoc}
 */
@Repository
public class AccidentJdbcRepository implements AccidentRepository {

    private final JdbcTemplate jdbc;

    public AccidentJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        return this.jdbc.query("select id, name, text, address, type, car_number, photo from accidents",
                this::assembleAccident);
    }

    @Override
    public Accident createAccident(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String toAccidents = "insert into accidents(name, text, address, type, car_number, photo, encoded_photo)"
                + "values (?, ?, ?, ?, ?, ?, ?);";
        String toAccidentRules = "insert into accident_rules(accident, rule) values (?, ?);";
        this.jdbc.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(toAccidents, new String[] {"id"});
            statement.setString(1, accident.getName());
            statement.setString(2, accident.getText());
            statement.setString(3, accident.getAddress());
            statement.setInt(4, accident.getType().getId());
            statement.setString(5, accident.getCarNumber());
            statement.setBytes(6, accident.getPhoto());
            statement.setString(7, accident.getEncodedPhoto());
            return statement;
        }, keyHolder);
        accident.setId(keyHolder.getKey().intValue());
        for (Rule rule : accident.getRules()) {
            this.jdbc.update(toAccidentRules, accident.getId(), rule.getId());
        }
        return accident;
    }

    @Override
    public Accident getById(int id) {
        return this.jdbc.queryForObject(
                "select id, name, text, address, type, car_number, photo from accidents where id = ?",
                this::assembleAccident, id);
    }

    @Override
    public void updateAccident(Accident accident) {
        String toAccidents = "update accidents set "
                + "name=?, text=?, address=?, type=?, car_number=?, photo=?, encoded_photo=? where id=?";
        String toAccidentRules = "insert into accident_rules(accident, rule) values (?, ?);";
        this.jdbc.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(toAccidents);
            statement.setString(1, accident.getName());
            statement.setString(2, accident.getText());
            statement.setString(3, accident.getAddress());
            statement.setInt(4, accident.getType().getId());
            statement.setString(5, accident.getCarNumber());
            statement.setBytes(6, accident.getPhoto());
            statement.setString(7, accident.getEncodedPhoto());
            statement.setInt(8, accident.getId());
            return statement;
        });
        this.jdbc.update("delete from accident_rules where accident = ?", accident.getId());
        for (Rule rule : accident.getRules()) {
            this.jdbc.update(toAccidentRules, accident.getId(), rule.getId());
        }
    }

    @Override
    public AccidentType getAccidentTypeById(int id) {
        return this.jdbc.queryForObject(
                "select id, name from types where id = ?",
                (resultSet, i) -> AccidentType.of(resultSet.getInt("id"), resultSet.getString("name")),
                id);
    }

    @Override
    public List<AccidentType> getAccidentTypes() {
        return this.jdbc.query(
                "select id, name from types",
                (resultSet, rowNum) ->
                        AccidentType.of(resultSet.getInt("id"), resultSet.getString("name"))

        );
    }

    @Override
    public Rule getRuleById(int id) {
        return this.jdbc.queryForObject(
                "select id, name from rules where id = ?",
                (resultSet, i) -> Rule.of(resultSet.getInt("id"), resultSet.getString("name")),
                id);
    }

    @Override
    public Set<Rule> getAllRules() {
        return new HashSet<>(this.jdbc.query(
                "select id, name from rules",
                (resultSet, rowNum) ->
                        Rule.of(resultSet.getInt("id"), resultSet.getString("name"))
        ));
    }

    private List<Rule> givenAccidentRules(int id) {
        return this.jdbc.query(
                "select rule from accident_rules where accident = ?",
                (set, row) -> getRuleById(set.getInt("rule")),
                id);
    }

    private Accident assembleAccident(ResultSet resultSet, int rowNumber) throws SQLException {
        Accident accident = Accident.of(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("text"),
                resultSet.getString("address"),
                getAccidentTypeById(resultSet.getInt("type")),
                resultSet.getString("car_number"),
                resultSet.getBytes("photo")
        );
        for (Rule rule : givenAccidentRules(resultSet.getInt("id"))) {
            accident.addRule(rule);
        }
        return accident;
    }
}
