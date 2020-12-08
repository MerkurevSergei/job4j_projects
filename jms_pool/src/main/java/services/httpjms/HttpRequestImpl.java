package services.httpjms;

import org.jetbrains.annotations.NotNull;
import services.server.http.HttpMethod;
import services.server.http.HttpRequest;

import java.util.Optional;
import java.util.StringJoiner;

/**
 * The {@code HttpRequestImpl} implemented {@code HttpRequest} interface
 * for client-server exchange for JMS application.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class HttpRequestImpl implements HttpRequest {
    private String requestLine = "";
    private final StringJoiner sjContent = new StringJoiner(System.lineSeparator());
    private final String sessionId;
    private int size = 0;

    /**
     *
     */
    public HttpRequestImpl() {
        this.sessionId = "0";
    }

    /**
     * @param sessionId init.
     */
    public HttpRequestImpl(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Add request line to request.
     *
     * @param line request for added to request.
     * @return HttpRequest with filled request line.
     * @throws IllegalStateException if request line corrupted.
     */
    @Override
    public HttpRequest addRequestLine(String line) throws IllegalStateException {
        line = Optional.ofNullable(line).orElse("");
        requestLine = line.replaceAll("\\s+", " ").trim();
        checkRequestLine();
        return this;
    }

    /**
     * Add content to request.
     *
     * @param line content for added to request
     * @return HttpRequest with filled content line.
     * @throws IllegalStateException if content line corrupted or
     *                               request line not filled.
     */
    @Override
    public HttpRequest addContent(String line) throws IllegalStateException {
        checkRequestLine();
        line = Optional.ofNullable(line).orElse("");
        sjContent.add(line);
        size++;
        checkSize();
        return this;
    }

    /**
     * @return {@code HttpMethod}.
     */
    @Override
    public HttpMethod getMethod() {
        return parseMethod(requestLine);
    }

    /**
     * @return uri string.
     */
    @Override
    public @NotNull String getURI() {
        return parseURI(requestLine);
    }

    /**
     * @return content string.
     */
    @Override
    public String getContent() {
        return sjContent.toString();
    }

    /**
     * @return session id string.
     */
    @Override
    public String getSessionId() {
        return sessionId;
    }

    /**
     * {@code HttpRequest} string for exchange.
     *
     * @return {@code HttpRequest}.
     */
    @Override
    public String toString() {
        String result = requestLine;
        String content = sjContent.toString();
        return content.isBlank() ? result : result + System.lineSeparator() + content;
    }

    private HttpMethod parseMethod(@NotNull String requestLine) throws IllegalArgumentException {
        final int i = requestLine.indexOf(" ");
        String strMethod = (i > -1) ? requestLine.substring(0, i) : requestLine;
        return HttpMethod.valueOf(strMethod);
    }

    private String parseURI(@NotNull String requestLine) {
        String uri = "/";
        final String[] s = requestLine.split(" ");
        if (s.length > 1) {
            uri = s[1].charAt(0) == '/' ? s[1] : "/" + s[1];
        }
        return uri;
    }

    private void checkRequestLine() throws IllegalStateException {
        try {
            parseMethod(requestLine);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(e);
        }
    }

    private void checkSize() throws IllegalStateException {
        if (size > 4) {
            throw new IllegalStateException("Request length is too long!");
        }
    }
}
