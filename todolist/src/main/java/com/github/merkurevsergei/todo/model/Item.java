package com.github.merkurevsergei.todo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The {@code Item} is Item entity from database.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = 0;
    private String title;
    private String description;
    private LocalDateTime created;
    private Boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OrderBy(value = "name")
    private Set<Category> categories = new LinkedHashSet<>();

    public Item(String title, String description, LocalDateTime created, Boolean done) {
        this.title = title;
        this.description = description;
        this.created = created;
        this.done = done;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }
}
