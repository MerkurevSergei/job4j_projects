package logic.usecase;

import logic.model.Turn;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface Game {
    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    Turn turn() throws Exception;

    /**
     * @return {@code true} if has next turn
     */
    boolean hasTurn();
}
