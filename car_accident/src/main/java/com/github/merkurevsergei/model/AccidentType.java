package com.github.merkurevsergei.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accident_types")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentType {
    @Id
    @EqualsAndHashCode.Include
    private int id;
    private String name;

    public static AccidentType of(int id, String name) {
        AccidentType accidentType = new AccidentType();
        accidentType.id = id;
        accidentType.name = name;
        return accidentType;
    }
}
