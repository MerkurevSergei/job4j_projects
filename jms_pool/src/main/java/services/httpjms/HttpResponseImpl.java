package services.httpjms;

import org.apache.commons.lang3.StringUtils;
import services.server.http.HttpResponse;

import java.util.Optional;
import java.util.StringJoiner;

/**
 * The {@code HttpResponseImpl} implemented {@code HttpResponse} interface
 * for client-server exchange for JMS application.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class HttpResponseImpl implements HttpResponse {
    private String responseLine;
    private final StringJoiner sjContent = new StringJoiner(System.lineSeparator());
    private int size = 0;

    /**
     * Add response line to response.
     *
     * @param line response for added to request.
     * @return HttpResponse with filled response line.
     * @throws IllegalStateException if response line corrupted.
     */
    @Override
    public HttpResponse addResponseLine(String line) throws IllegalStateException {
        line = Optional.ofNullable(line).orElse("");
        responseLine = line;
        checkResponseLine();
        return this;
    }

    /**
     * Add content to response.
     *
     * @param line content for added to response
     * @return HttpResponse with filled content line.
     * @throws IllegalStateException if content line corrupted or
     *                               response line not filled.
     */
    @Override
    public HttpResponse addContent(String line) {
        checkResponseLine();
        line = Optional.ofNullable(line).orElse("");
        sjContent.add(line);
        size++;
        checkSize();
        return this;
    }

    @Override
    public String toString() {
        String result = responseLine;
        String content = sjContent.toString();
        return content.isBlank() ? result : result + System.lineSeparator() + content;
    }

    private void checkResponseLine() throws IllegalStateException {
        final String[] strings = responseLine.split(" ");
        if (strings.length < 2
                || !strings[0].equals("HTTP/1.1")
                || !StringUtils.isNumeric(strings[1])) {
            throw new IllegalStateException("Bad response line!");
        }
    }

    private void checkSize() throws IllegalStateException {
        if (size > 4) {
            throw new IllegalStateException("Response length is too long!");
        }
    }
}
