package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.message.Message;
import domain.message.TopicMessage;
import domain.usecases.TopicBroker;
import org.slf4j.LoggerFactory;
import services.server.http.HttpRequest;
import services.server.http.HttpResponse;
import views.View;
import views.ViewCommon;

/**
 * The {@code TopicController} implemented {@code Controller} interface.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class TopicController implements Controller {
    private final TopicBroker broker = new TopicBroker();
    private final View view = new ViewCommon();

    /**
     * @param httpRequest  request from client.
     * @param httpResponse response for client.
     */
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        final String queueName = httpRequest.getURI().split("/")[2];
        final Message msg = broker.getMessage(queueName, httpRequest.getSessionId());
        String content = null;
        try {
            content = (msg != null) ? new ObjectMapper().writeValueAsString(msg) : null;
        } catch (JsonProcessingException e) {
            LoggerFactory.getLogger(TopicController.class).error("The message is corrupted", e);
        }

        if (content != null) {
            view.showGetSuccess(httpResponse, content);
        } else {
            view.showGetNoContent(httpResponse);
        }
    }

    /**
     * @param httpRequest  request from client.
     * @param httpResponse response for client.
     */
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        Message msg = null;
        try {
            msg = new ObjectMapper().readValue(httpRequest.getContent(), TopicMessage.class);
        } catch (JsonProcessingException e) {
            LoggerFactory.getLogger(TopicController.class).error("The message is corrupted", e);
        }

        if (msg != null) {
            broker.addMessage(msg.getHeader(), msg);
            view.showPostSuccess(httpResponse);
        } else {
            view.showPostMessageCorrupted(httpResponse);
        }
    }
}
