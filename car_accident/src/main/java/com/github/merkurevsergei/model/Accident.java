package com.github.merkurevsergei.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "accidents")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Accident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String text;
    private String address;
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private AccidentType type;
    @ManyToMany
    @JoinTable(name = "accidents_accident_rules",
            joinColumns = { @JoinColumn(name = "accident_id") },
            inverseJoinColumns = { @JoinColumn(name = "rule_id") })
    private Set<AccidentRule> rules;

    public Accident(int id, String name, String text, String address, AccidentType type) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.address = address;
        this.type = type;
        this.rules = new LinkedHashSet<>();
    }

    public void addRule(AccidentRule rule) {
        rules.add(rule);
    }
}
