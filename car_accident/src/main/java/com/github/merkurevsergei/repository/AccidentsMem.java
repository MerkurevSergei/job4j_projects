package com.github.merkurevsergei.repository;

import com.github.merkurevsergei.model.Accident;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Getter
public class AccidentsMem {
    private final Map<Integer, Accident> accidents = new HashMap<>();

    public void create(Accident accident) {
        accidents.put(accident.getId(), accident);
    }

    public Accident findById(int id) {
        return accidents.get(id);
    }
}
