package services.server.http;

/**
 * The {@code HttpResponse} interface for client-server exchange.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface HttpResponse {

    /**
     * Add response line to response.
     *
     * @param line response for added to request.
     * @return HttpResponse with filled response line.
     * @throws IllegalStateException if response line corrupted.
     */
    HttpResponse addResponseLine(String line) throws IllegalStateException;

    /**
     * Add content to response.
     *
     * @param line content for added to response
     * @return HttpResponse with filled content line.
     * @throws IllegalStateException if content line corrupted or
     *                               response line not filled.
     */
    HttpResponse addContent(String line) throws IllegalStateException;
}
