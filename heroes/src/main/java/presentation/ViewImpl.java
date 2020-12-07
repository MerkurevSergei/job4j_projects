package presentation;

import logic.model.Turn;

/**
 * The View interface implementation.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public class ViewImpl implements View {
    /**
     * Show view.
     */
    @Override
    public void show(Turn turn) {
        String message = "";
        message += turn.getActiveUnitRace() + " vs " + turn.getTargetUnitRace() + ". ";
        message += turn.getActiveUnit() + " " + turn.actionInfo() + " " + turn.getTargetUnit() + ". ";
        message += "remain health: " + turn.getTargetHealth();
        System.out.println(message);
    }
}
