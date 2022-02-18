package com.github.merkurevsergei.service;

import com.github.merkurevsergei.model.Accident;
import com.github.merkurevsergei.model.AccidentRule;
import com.github.merkurevsergei.model.AccidentType;
import com.github.merkurevsergei.repository.AccidentRepository;
import com.github.merkurevsergei.repository.AccidentRuleRepository;
import com.github.merkurevsergei.repository.AccidentTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccidentService {
    private final AccidentRepository accidentsDAO;
    private final AccidentTypeRepository accidentTypesDAO;
    private final AccidentRuleRepository accidentRulesDAO;

    public AccidentService(AccidentRepository accidentsDAO,
                           AccidentTypeRepository accidentTypesDAO,
                           AccidentRuleRepository accidentRulesDAO) {
        this.accidentsDAO = accidentsDAO;
        this.accidentTypesDAO = accidentTypesDAO;
        this.accidentRulesDAO = accidentRulesDAO;
    }

    public List<Accident> findAllAccidents() {
        return StreamSupport
                .stream(accidentsDAO.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Accident> findAccidentById(int id) {
        return accidentsDAO.findById(id);
    }

    public Optional<Accident> save(Accident accident) {
        return Optional.of(accidentsDAO.save(accident));
    }

    public Collection<AccidentType> findAllTypes() {
        return StreamSupport
                .stream(accidentTypesDAO.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Collection<AccidentRule> findAllRules() {
        return StreamSupport
                .stream(accidentRulesDAO.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
