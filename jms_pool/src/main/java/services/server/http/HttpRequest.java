package services.server.http;

import org.jetbrains.annotations.NotNull;

/**
 * The {@code HttpRequest} interface for client-server exchange.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface HttpRequest {

    /**
     * Add request line to request.
     *
     * @param line request for added to request.
     * @return HttpRequest with filled request line.
     * @throws IllegalStateException if request line corrupted.
     */
    HttpRequest addRequestLine(String line) throws IllegalStateException;

    /**
     * Add content to request.
     *
     * @param line content for added to request
     * @return HttpRequest with filled content line.
     * @throws IllegalStateException if content line corrupted or
     *                               request line not filled.
     */
    HttpRequest addContent(String line) throws IllegalStateException;

    /**
     * @return {@code HttpMethod}.
     */
    HttpMethod getMethod();

    /**
     * @return uri string.
     */
    @NotNull String getURI();

    /**
     * @return content string.
     */
    String getContent();

    /**
     * @return session id string.
     */
    String getSessionId();
}
