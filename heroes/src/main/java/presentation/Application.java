package presentation;

import logic.model.Turn;
import logic.usecase.Game;
import logic.usecase.GameImpl;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * The Application.
 * Application controller is entry point and implement presentation logic,
 * intermediate layer between view layer and logic layer.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public class Application {

    /**
     * The game instance.
     */
    private final Game game;

    /**
     * The view instance.
     */
    private final View view;

    /**
     * Application constructor.
     */
    public Application(String logFile) {
        this.game = new GameImpl(logFile);
        this.view = new ViewImpl();
    }

    /**
     * Check next turn in the application.
     * Returns {@code true} if the game can take the next step.
     *
     * @return {@code true} if the game can take the next step
     */
    public final boolean hasTurn() {
        return game.hasTurn();
    }

    /**
     * Takes the next turn in the application.
     *
     * @throws NoSuchElementException if the iteration has no more elements
     */
    public final void turn() throws Exception {
        Turn turn = game.turn();
        view.show(turn);
    }

}
