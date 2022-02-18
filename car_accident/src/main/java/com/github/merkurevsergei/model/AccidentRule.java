package com.github.merkurevsergei.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accident_rules")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentRule {
    @Id
    @EqualsAndHashCode.Include
    private int id;
    private String name;

    public static AccidentRule of(int id, String name) {
        AccidentRule rule = new AccidentRule();
        rule.id = id;
        rule.name = name;
        return rule;
    }
}
