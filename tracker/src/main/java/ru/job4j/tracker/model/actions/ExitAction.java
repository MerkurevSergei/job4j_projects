package ru.job4j.tracker.model.actions;

import org.springframework.stereotype.Component;
import ru.job4j.tracker.services.input.Input;
import ru.job4j.tracker.store.Store;

@Component
public class ExitAction implements UserAction {
    @Override
    public String name() {
        return "=== Exit program ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        return false;
    }

    @Override
    public int getOrder() {
        return 6;
    }
}
