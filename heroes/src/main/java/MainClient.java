import presentation.Application;

/**
 * The Main client.
 * Simulate user actions.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public class MainClient {

    /**
     * The MainClient simulate user behaviour
     *
     * @param args console arguments
     */
    public static void main(String[] args) throws Exception {
        String fileName = args.length > 0 ? args[0] : "";
        if (fileName.isEmpty()) {
            System.out.println("File log not define.");
        }

        var app = new Application(fileName);
        while (app.hasTurn()) {
            app.turn();
        }
        System.out.println("Log file see:" + fileName);
    }
}
