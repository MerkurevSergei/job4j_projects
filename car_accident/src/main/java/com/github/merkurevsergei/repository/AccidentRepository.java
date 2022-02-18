package com.github.merkurevsergei.repository;

import com.github.merkurevsergei.model.Accident;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccidentRepository extends CrudRepository<Accident, Integer> {

    @Override
    @Transactional(readOnly = true)
    @Query("select distinct a from Accident a left join fetch a.rules where a.id = ?1")
    @NotNull
    Optional<Accident> findById(@NotNull Integer id);

    @Override
    @Transactional(readOnly = true)
    @Query("select distinct a from Accident a left join fetch a.rules")
    @NotNull
    Iterable<Accident> findAll();
}
