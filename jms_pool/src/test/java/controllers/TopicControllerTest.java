package controllers;

import org.junit.Test;
import services.httpjms.HttpRequestImpl;
import services.httpjms.HttpResponseImpl;
import services.server.http.HttpRequest;
import services.server.http.HttpResponse;

import static org.junit.Assert.*;

public class TopicControllerTest {

    @Test
    public void whenSuccessDoPostDoGet() {
        // Add message to queue
        final TopicController controller = new TopicController();
        HttpRequest httpRequest = new HttpRequestImpl()
                .addRequestLine("POST /topic HTTP/1.1")
                .addContent("{\"topic\":\"weather\",\"text\":\"temperature +18 C\"}");
        HttpResponse httpResponse = new HttpResponseImpl();

        controller.doPost(httpRequest, httpResponse);
        assertEquals("HTTP/1.1 200 OK" + System.lineSeparator()
                        + "The message was successfully added!",
                httpResponse.toString());

        // Get message from queue
        httpRequest = new HttpRequestImpl()
                .addRequestLine("GET /topic/weather HTTP/1.1");
        httpResponse = new HttpResponseImpl();

        controller.doGet(httpRequest, httpResponse);
        assertEquals("HTTP/1.1 200 OK" + System.lineSeparator()
                        + "{\"topic\":\"weather\",\"text\":\"temperature +18 C\"}",
                httpResponse.toString());
    }

    @Test
    public void whenNoContentDoGet() {
        final TopicController controller = new TopicController();
        HttpRequest httpRequest = new HttpRequestImpl()
                .addRequestLine("GET /topic/weather HTTP/1.1");
        HttpResponse httpResponse = new HttpResponseImpl();
        controller.doGet(httpRequest, httpResponse);
        assertEquals("HTTP/1.1 204 No Content", httpResponse.toString());
    }

    @Test
    public void whenFailedAddedNullMessageDoPost() {
        final TopicController controller = new TopicController();
        HttpRequest httpRequest = new HttpRequestImpl()
                .addRequestLine("POST /topic HTTP/1.1")
                .addContent(null);
        HttpResponse httpResponse = new HttpResponseImpl();

        controller.doPost(httpRequest, httpResponse);
        assertEquals("HTTP/1.1 200 OK" + System.lineSeparator()
                + "The corrupted message was failed added!", httpResponse.toString());
    }

    @Test
    public void whenFailedAddedCorruptedMessageDoPost() {
        final TopicController controller = new TopicController();
        HttpRequest httpRequest = new HttpRequestImpl()
                .addRequestLine("POST /topic HTTP/1.1")
                .addContent("{\"text\":\"temperature +18 C\"}");
        HttpResponse httpResponse = new HttpResponseImpl();

        controller.doPost(httpRequest, httpResponse);
        assertEquals("HTTP/1.1 200 OK" + System.lineSeparator()
                + "The corrupted message was failed added!", httpResponse.toString());
    }
}