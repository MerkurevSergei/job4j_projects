package ru.job4j.tracker.model.actions;

import org.springframework.stereotype.Component;
import ru.job4j.tracker.services.input.Input;
import ru.job4j.tracker.store.Store;

@Component
public class DeleteAction implements UserAction {
    @Override
    public String name() {
        return "=== Delete Item ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        Integer id = input.askInt("Enter id: ");
        boolean rsl = tracker.delete(id);
        if (!rsl) {
            System.out.println("Item not found!");
        } else {
            System.out.println("Item deleted!");
        }
        return true;
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
