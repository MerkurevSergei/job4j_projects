package controllers;

import org.junit.Test;
import services.httpjms.HttpRequestImpl;
import services.httpjms.HttpResponseImpl;
import services.server.http.HttpRequest;
import services.server.http.HttpResponse;

import static org.junit.Assert.*;

public class DefaultControllerTest {

    @Test
    public void doPostDoGet() {
        // Add message to queue
        final DefaultController controller = new DefaultController();
        HttpRequest httpRequest = new HttpRequestImpl()
                .addRequestLine("POST /any HTTP/1.1")
                .addContent("{\"queue\":\"weather\",\"text\":\"temperature +18 C\"}");
        HttpResponse httpResponse = new HttpResponseImpl();

        controller.doPost(httpRequest, httpResponse);
        assertEquals("HTTP/1.1 200 OK" + System.lineSeparator() + "The exchange type is not found!",
                httpResponse.toString());

        // Get message from queue
        httpRequest = new HttpRequestImpl()
                .addRequestLine("GET /any/weather HTTP/1.1");
        httpResponse = new HttpResponseImpl();

        controller.doGet(httpRequest, httpResponse);
        assertEquals("HTTP/1.1 200 OK" + System.lineSeparator() + "The exchange type is not found!",
                httpResponse.toString());
    }

}