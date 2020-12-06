package ru.job4j.tracker.model.actions;

import org.springframework.stereotype.Component;
import ru.job4j.tracker.services.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

@Component
public class CreateAction implements UserAction {

    @Override
    public String name() {
        return "=== Create a new Item ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        String name = input.askStr("Enter name: ");
        Item item = new Item(name);
        tracker.add(item);
        return true;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
