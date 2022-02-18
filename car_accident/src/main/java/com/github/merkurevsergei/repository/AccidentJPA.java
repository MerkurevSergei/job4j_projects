package com.github.merkurevsergei.repository;

import com.github.merkurevsergei.model.Accident;
import com.github.merkurevsergei.model.AccidentRule;
import com.github.merkurevsergei.model.AccidentType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AccidentJPA {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Accident> findAllAccidents() {
        return em.createQuery("select distinct a "
                        + "from Accident a "
                        + "left join fetch a.rules",
                Accident.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Optional<Accident> findAccidentById(int id) {
        return em.createQuery("select distinct a "
                        + "from Accident a "
                        + "left join fetch a.rules where a.id = :id", Accident.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<Accident> update(Accident accident) {
        return Optional.ofNullable(em.merge(accident));
    }

    public Optional<Accident> create(Accident accident) {
        em.persist(accident);
        return Optional.of(accident);
    }

    @Transactional(readOnly = true)
    public Collection<AccidentType> findAllTypes() {
        return em.createQuery("select t from AccidentType t", AccidentType.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Collection<AccidentRule> findAllRules() {
        return em.createQuery("select r from AccidentRule r", AccidentRule.class)
                .getResultList();
    }
}
