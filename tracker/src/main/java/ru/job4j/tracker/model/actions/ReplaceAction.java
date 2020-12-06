package ru.job4j.tracker.model.actions;

import org.springframework.stereotype.Component;
import ru.job4j.tracker.services.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

@Component
public class ReplaceAction implements UserAction {
    @Override
    public String name() {
        return "=== Replace Item ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        Integer id = input.askInt("Enter id: ");
        Item item = tracker.findById(id);
        if (item == null) {
            System.out.println("Item not found!");
        } else {
            Item newItem = new Item(input.askStr("Enter new name:"));
            newItem.setId(item.getId());
            tracker.replace(item.getId(), newItem);
            System.out.println("Item edit success!");
        }
        return true;
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
