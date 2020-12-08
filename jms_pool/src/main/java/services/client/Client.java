package services.client;

import services.server.http.HttpRequest;
import services.server.http.HttpResponse;

import java.io.IOException;

/**
 * The {@code Client} interface.
 * Used {@code HttpRequest} and {@code HttpRequest} from
 * package {@code services.server.http}.
 * Connect to server, send and receive messages.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface Client {

    /**
     * Start client and connect to server ip:port.
     *
     * @param ip   address.
     * @param port listening.
     * @throws IOException if an I/O error occurs.
     */
    void start(String ip, int port) throws IOException;

    /**
     * Send request to server and receive response.
     *
     * @param httpRequest request to send.
     * @return HttpResponse.
     * @throws IOException if an I/O error occurs.
     */
    HttpResponse send(HttpRequest httpRequest) throws IOException;

    /**
     * Close server.
     *
     * @throws IOException if an I/O error occurs.
     */
    void close() throws IOException;
}
