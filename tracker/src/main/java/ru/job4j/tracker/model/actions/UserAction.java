package ru.job4j.tracker.model.actions;

import org.springframework.core.Ordered;
import ru.job4j.tracker.services.input.Input;
import ru.job4j.tracker.store.Store;

public interface UserAction extends Ordered {
    String name();

    boolean execute(Input input, Store tracker);
}