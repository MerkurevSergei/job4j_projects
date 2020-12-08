package application;

import controllers.DefaultController;
import controllers.QueueController;
import controllers.TopicController;
import org.jetbrains.annotations.NotNull;
import services.server.Application;
import services.server.http.HttpRequest;
import services.server.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * The {@code JMS} is implemented {@code services.server.Application}
 * interface for {@code services.server.Server} and FrontController
 * pattern and it is entry point to application JMS.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class JMS implements Application {
    private final Map<String, BiConsumer<HttpRequest, HttpResponse>> routes = new HashMap<>();

    /**
     * Define routes.
     */
    public JMS() {
        final QueueController queueController = new QueueController();
        final TopicController topicController = new TopicController();
        routes.put("^GET /queue/\\w+$", queueController::doGet);
        routes.put("^POST /queue$", queueController::doPost);
        routes.put("^GET /topic/\\w+$", topicController::doGet);
        routes.put("^POST /topic$", topicController::doPost);
    }

    /**
     * Execute request from client and return response.
     *
     * @param httpRequest  request from client.
     * @param httpResponse response for client.
     */
    @Override
    public void execute(@NotNull HttpRequest httpRequest, @NotNull HttpResponse httpResponse) {
        httpRequest = Optional.of(httpRequest).get();
        httpResponse = Optional.of(httpResponse).get();
        BiConsumer<HttpRequest, HttpResponse> action = dispatch(httpRequest);
        action.accept(httpRequest, httpResponse);
    }

    /**
     * Get controller method from request aka action.
     *
     * @param httpRequest consist string for define method.
     * @return action.
     */
    private BiConsumer<HttpRequest, HttpResponse> dispatch(HttpRequest httpRequest) {
        BiConsumer<HttpRequest, HttpResponse> action = new DefaultController()::doGet;
        final String requestPattern = httpRequest.getMethod() + " " + httpRequest.getURI();
        for (Map.Entry<String, BiConsumer<HttpRequest, HttpResponse>> route : routes.entrySet()) {
            action = requestPattern.matches(route.getKey()) ? route.getValue() : action;
        }
        return action;
    }
}
