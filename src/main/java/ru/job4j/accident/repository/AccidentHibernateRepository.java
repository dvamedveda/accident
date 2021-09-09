package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.entities.Accident;
import ru.job4j.accident.entities.AccidentType;
import ru.job4j.accident.entities.Rule;

import java.util.*;

@Repository
public class AccidentHibernateRepository implements AccidentRepository {

    private final SessionFactory sessionFactory;

    public AccidentHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("from Accident a", Accident.class).list();
        }
    }

    @Override
    public Accident createAccident(Accident accident) {
        try (Session session = this.sessionFactory.openSession()) {
            session.save(accident);
            return accident;
        }
    }

    @Override
    public Accident getById(int id) {
        try (Session session = this.sessionFactory.openSession()) {
            return session
                    .createQuery("select distinct a from Accident a "
                            + "join fetch a.rules "
                            + "join fetch a.type "
                            + "where a.id = :id", Accident.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    @Override
    public void updateAccident(Accident accident) {
        try (Session session = this.sessionFactory.openSession()) {
            session.update(accident);
        }
    }

    @Override
    public AccidentType getAccidentTypeById(int id) {
        AccidentType result;
        try (Session session = this.sessionFactory.openSession()) {
            result = session
                    .createQuery("from AccidentType t where t.id = :id order by t.id asc", AccidentType.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
        return result;
    }

    @Override
    public List<AccidentType> getAccidentTypes() {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("select t from AccidentType t order by t.id asc", AccidentType.class).getResultList();
        }
    }

    @Override
    public Rule getRuleById(int id) {
        Rule result;
        try (Session session = this.sessionFactory.openSession()) {
            result = session
                    .createQuery("from Rule r where r.id = :id order by r.id asc", Rule.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
        return result;
    }

    @Override
    public Set<Rule> getAllRules() {
        try (Session session = this.sessionFactory.openSession()) {
            return new HashSet<>(session.createQuery("from Rule r order by r.id asc", Rule.class).getResultList());
        }
    }
}
