package controllers;

import services.server.http.HttpRequest;
import services.server.http.HttpResponse;
import views.View;
import views.ViewCommon;

/**
 * The {@code DefaultController} implemented {@code Controller} interface.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class DefaultController implements Controller {
    private final View view = new ViewCommon();

    /**
     * @param httpRequest  request from client.
     * @param httpResponse response for client.
     */
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        view.showExchangeTypeNotFound(httpResponse);
    }

    /**
     * @param httpRequest  request from client.
     * @param httpResponse response for client.
     */
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        view.showExchangeTypeNotFound(httpResponse);
    }
}
