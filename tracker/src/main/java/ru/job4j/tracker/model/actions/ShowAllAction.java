package ru.job4j.tracker.model.actions;

import org.springframework.stereotype.Component;
import ru.job4j.tracker.services.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

import java.util.List;

@Component
public class ShowAllAction implements UserAction {
    @Override
    public String name() {
        return "=== Print all Item ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        List<Item> items = tracker.findAll();
        for (Item item : items) {
            System.out.println(item);
        }
        return true;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
