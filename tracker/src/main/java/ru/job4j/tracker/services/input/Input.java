package ru.job4j.tracker.services.input;

public interface Input {
    String askStr(String question);

    int askInt(String question);
}