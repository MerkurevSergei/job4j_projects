package services.httpjms;

import org.junit.Test;
import services.server.http.HttpMethod;
import services.server.http.HttpRequest;

import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class HttpRequestImplTest {

    @Test
    public void addFullGetRequestLineGetMethodGetURI() {
        final HttpRequestImpl httpRequest = new HttpRequestImpl();
        httpRequest.addRequestLine("GET /topic/weather HTTP/1.1");
        assertEquals(HttpMethod.GET, httpRequest.getMethod());
        assertEquals("/topic/weather", httpRequest.getURI());
    }

    @Test
    public void addFullPostRequestLineGetMethodGetURI() {
        final HttpRequestImpl httpRequest = new HttpRequestImpl();
        httpRequest.addRequestLine("POST /queue HTTP/1.1");
        assertEquals(HttpMethod.POST, httpRequest.getMethod());
        assertEquals("/queue", httpRequest.getURI());
    }

    @Test
    public void addOnlyMethodRequestLineGetMethodGetURI() {
        final HttpRequestImpl httpRequest = new HttpRequestImpl();
        httpRequest.addRequestLine("POST");
        assertEquals(HttpMethod.POST, httpRequest.getMethod());
        assertEquals("/", httpRequest.getURI());
    }

    @Test
    public void addContentGetContent() {
        final StringJoiner sj = new StringJoiner(System.lineSeparator())
                .add("{")
                .add("\"queue\" : \"weather\",")
                .add("\"text\" : \"temperature +18 C\"")
                .add("}");
        final HttpRequest httpRequest = new HttpRequestImpl()
                .addRequestLine("POST /queue HTTP/1.1")
                .addContent(sj.toString());
        assertEquals(sj.toString(), httpRequest.getContent());
    }

    @Test
    public void correctRequestToString() {
        final StringJoiner sj = new StringJoiner(System.lineSeparator())
                .add("{")
                .add("\"queue\" : \"weather\",")
                .add("\"text\" : \"temperature +18 C\"")
                .add("}");
        final HttpRequest httpRequest = new HttpRequestImpl()
                .addRequestLine("POST /queue HTTP/1.1")
                .addContent(sj.toString());
        assertEquals("POST /queue HTTP/1.1" + System.lineSeparator() + sj.toString(),
                httpRequest.toString());
    }

    @Test(expected = IllegalStateException.class)
    public void addCorruptedRequestLineIllegalStateException() {
        final HttpRequestImpl httpRequest = new HttpRequestImpl();
        httpRequest.addContent("/queue");
        assertEquals("/", httpRequest.getURI());
    }

    @Test(expected = IllegalStateException.class)
    public void addWithoutRequestLineIllegalStateException() {
        final HttpRequestImpl httpRequest = new HttpRequestImpl();
        httpRequest.addContent(null);
        assertEquals("/", httpRequest.getURI());
    }

    @Test(expected = IllegalStateException.class)
    public void largeResponse() {
        final HttpRequestImpl httpRequest = new HttpRequestImpl();
        httpRequest
                .addRequestLine("HTTP/1.1 400 Bad Request").addContent("1")
                .addContent("2").addContent("3").addContent("4").addContent("5");
        fail();
    }
}