package ru.job4j.tracker.model.actions;

import org.springframework.stereotype.Component;
import ru.job4j.tracker.services.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

import java.util.List;

@Component
public class FindByName implements UserAction {
    @Override
    public String name() {
        return "=== Find items by name ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        String name = input.askStr("Enter name: ");
        List<Item> items = tracker.findByName(name);
        System.out.println("Founded: " + items.size() + " items");
        for (Item item: items) {
            System.out.println(item);
        }
        return true;
    }

    @Override
    public int getOrder() {
        return 5;
    }
}
