package ru.job4j.tracker.model.actions;

import org.springframework.stereotype.Component;
import ru.job4j.tracker.services.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

@Component
public class FindByIdAction implements UserAction {
    @Override
    public String name() {
        return "=== Find item by Id ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        Integer id = input.askInt("Enter id: ");
        Item item = tracker.findById(id);
        if (item == null) {
            System.out.println("Item not found!");
        } else {
            System.out.println("Item founded: " + item);
        }
        return true;
    }

    @Override
    public int getOrder() {
        return 4;
    }
}
