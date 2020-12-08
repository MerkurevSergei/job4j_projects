package services.server;

import services.server.http.HttpRequest;
import services.server.http.HttpResponse;

/**
 * The {@code Application} interface is FrontController pattern.
 * The {@code Server} transfers control to this interface
 * after receiving the request.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface Application {

    /**
     * Execute request from client and return response.
     *
     * @param httpRequest  request from client.
     * @param httpResponse response for client.
     */
    void execute(HttpRequest httpRequest, HttpResponse httpResponse);
}
