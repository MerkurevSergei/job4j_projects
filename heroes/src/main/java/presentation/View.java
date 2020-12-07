package presentation;

import logic.model.Turn;

/**
 * The View interface.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface View {
    /**
     * Show view.
     */
    void show(Turn turn);
}
