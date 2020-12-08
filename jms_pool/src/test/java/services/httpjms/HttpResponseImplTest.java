package services.httpjms;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class HttpResponseImplTest {

    @Test
    public void correctResponseToString() {
        final HttpResponseImpl httpResponse = new HttpResponseImpl();
        httpResponse.addResponseLine("HTTP/1.1 400 Bad Request")
                .addContent("Hello");
        assertEquals(
                "HTTP/1.1 400 Bad Request" + System.lineSeparator()
                        + "Hello",
                httpResponse.toString());
    }

    @Test(expected = IllegalStateException.class)
    public void responseLineNotHTTPProtocol() {
        final HttpResponseImpl httpResponse = new HttpResponseImpl();
        httpResponse.addResponseLine("H/1.1 400 Bad Request");
        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void responseLineBadCode() {
        final HttpResponseImpl httpResponse = new HttpResponseImpl();
        httpResponse.addResponseLine("HTTP/1.1 40P Bad Request");
        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void addCorruptedLessParams() {
        final HttpResponseImpl httpResponse = new HttpResponseImpl();
        httpResponse.addResponseLine("HTTP/1.1");
        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void largeResponse() {
        final HttpResponseImpl httpResponse = new HttpResponseImpl();
        httpResponse
                .addResponseLine("HTTP/1.1 400 Bad Request").addContent("1")
                .addContent("2").addContent("3").addContent("4").addContent("5");
        fail();
    }
}