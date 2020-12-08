package controllers;

import org.junit.Test;
import services.httpjms.HttpRequestImpl;
import services.httpjms.HttpResponseImpl;
import services.server.http.HttpRequest;
import services.server.http.HttpResponse;

import static org.junit.Assert.assertEquals;

public class QueueControllerTest {

    @Test
    public void whenSuccessDoPostDoGet() {
        // Add message to queue
        final QueueController controller = new QueueController();
        HttpRequest httpRequest = new HttpRequestImpl()
                .addRequestLine("POST /queue HTTP/1.1")
                .addContent("{\"queue\":\"weather\",\"text\":\"temperature +18 C\"}");
        HttpResponse httpResponse = new HttpResponseImpl();

        controller.doPost(httpRequest, httpResponse);
        assertEquals("HTTP/1.1 200 OK" + System.lineSeparator()
                        + "The message was successfully added!",
                httpResponse.toString());

        // Get message from queue
        httpRequest = new HttpRequestImpl()
                .addRequestLine("GET /queue/weather HTTP/1.1");
        httpResponse = new HttpResponseImpl();

        controller.doGet(httpRequest, httpResponse);
        assertEquals("HTTP/1.1 200 OK" + System.lineSeparator()
                        + "{\"queue\":\"weather\",\"text\":\"temperature +18 C\"}",
                httpResponse.toString());
    }

    @Test
    public void whenNoContentDoGet() {
        final QueueController controller = new QueueController();
        HttpRequest httpRequest = new HttpRequestImpl()
                .addRequestLine("GET /queue/weather HTTP/1.1");
        HttpResponse httpResponse = new HttpResponseImpl();
        controller.doGet(httpRequest, httpResponse);
        assertEquals("HTTP/1.1 204 No Content", httpResponse.toString());
    }

    @Test
    public void whenFailedAddedNullMessageDoPost() {
        final QueueController controller = new QueueController();
        HttpRequest httpRequest = new HttpRequestImpl()
                .addRequestLine("POST /queue HTTP/1.1")
                .addContent(null);
        HttpResponse httpResponse = new HttpResponseImpl();

        controller.doPost(httpRequest, httpResponse);
        assertEquals("HTTP/1.1 200 OK" + System.lineSeparator()
                        + "The corrupted message was failed added!",
                httpResponse.toString());
    }

    @Test
    public void whenFailedAddedCorruptedMessageDoPost() {
        final QueueController controller = new QueueController();
        HttpRequest httpRequest = new HttpRequestImpl()
                .addRequestLine("POST /queue HTTP/1.1")
                .addContent("{\"text\":\"temperature +18 C\"}");
        HttpResponse httpResponse = new HttpResponseImpl();

        controller.doPost(httpRequest, httpResponse);
        assertEquals("HTTP/1.1 200 OK" + System.lineSeparator()
                        + "The corrupted message was failed added!",
                httpResponse.toString());
    }
}