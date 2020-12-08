package services.server;

import org.slf4j.LoggerFactory;
import services.server.session.SessionBroker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The {@code Server} for client-server exchange.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class Server {
    private final int port;
    private ServerSocket server;
    private final Application application;
    private final ExecutorService executorService = Executors.newWorkStealingPool();
    private final SessionBroker sessionBroker;

    /**
     * @param application   where the request is sent and response received.
     * @param sessionBroker session manager.
     */
    public Server(Application application, SessionBroker sessionBroker) {
        this.application = application;
        this.port = 49001;
        this.sessionBroker = sessionBroker;
    }

    /**
     * @param application   where the request is sent and response received.
     * @param port          custom for connection.
     * @param sessionBroker session manager.
     */
    public Server(Application application, int port, SessionBroker sessionBroker) {
        this.application = application;
        this.port = port;
        this.sessionBroker = sessionBroker;
    }

    /**
     * Start server and accept connection.
     */
    public void start() {
        LoggerFactory.getLogger(Server.class).debug("SERVER: The server is running.");
        try {
            server = new ServerSocket(port);
            while (!server.isClosed()) {
                Socket client = server.accept();
                executorService.submit(new ClientHandler(client, application, sessionBroker));
            }
        } catch (IOException e) {
            LoggerFactory.getLogger(Server.class).debug("SERVER: The server is stopped!!!");
        }
    }

    /**
     * Close connection and stop server.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void close() throws IOException {
        executorService.shutdown();
        server.close();
    }
}