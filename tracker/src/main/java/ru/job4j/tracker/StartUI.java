package ru.job4j.tracker;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.job4j.tracker.model.actions.UserAction;
import ru.job4j.tracker.services.input.Input;
import ru.job4j.tracker.store.Store;

import java.util.List;

@Component
@Scope("prototype")
public class StartUI {

    private final Input input;
    private final Store store;
    private final List<UserAction> actions;

    public StartUI(Input input, Store store, List<UserAction> actions) {
        this.input = input;
        this.store = store;
        this.actions = actions;
    }

    public void start() {
        store.init();
        boolean run = true;
        while (run) {
            this.showMenu();
            int selected = input.askInt("Select: ");
            UserAction action = actions.get(selected);
            run = action.execute(input, store);
        }
    }

    private void showMenu() {
        System.out.println();
        System.out.println("Menu.");
        for (UserAction action : actions) {
            System.out.println(action.getOrder() + ". " + action.name());
        }
    }


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.job4j.tracker");

        context.refresh();
        StartUI app = context.getBean(StartUI.class);
        app.start();
    }
}