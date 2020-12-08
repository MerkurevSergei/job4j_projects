package controllers;

import services.server.http.HttpRequest;
import services.server.http.HttpResponse;

/**
 * The {@code Controller} interface.
 */
public interface Controller {

    /**
     * @param httpRequest  request from client.
     * @param httpResponse response for client.
     */
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse);

    /**
     * @param httpRequest  request from client.
     * @param httpResponse response for client.
     */
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
