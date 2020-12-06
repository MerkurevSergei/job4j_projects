package ru.job4j.tracker.model.actions;

import ru.job4j.tracker.services.input.Input;
import ru.job4j.tracker.store.Store;

public class StubAction implements UserAction {
    private boolean call = false;

    @Override
    public String name() {
        return "Stub action";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        call = true;
        return false;
    }

    public boolean isCall() {
        return call;
    }

    @Override
    public int getOrder() {
        return 99;
    }
}