package views;

import services.server.http.HttpResponse;

/**
 * The {@code View} interface for filling
 * the {@code services.server.http.httpResponse}
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface View {

    /**
     * When show post success.
     *
     * @param httpResponse for filling.
     */
    void showPostSuccess(HttpResponse httpResponse);

    /**
     * When show post failed adding message.
     *
     * @param httpResponse for filling.
     */
    void showPostMessageCorrupted(HttpResponse httpResponse);

    /**
     * When show get success.
     *
     * @param httpResponse for filling.
     */
    void showGetSuccess(HttpResponse httpResponse, String msg);

    /**
     * When show get no content.
     *
     * @param httpResponse for filling.
     */
    void showGetNoContent(HttpResponse httpResponse);

    /**
     * When show exchange type not found,
     * request was been corrupted or invalid.
     *
     * @param httpResponse for filling.
     */
    void showExchangeTypeNotFound(HttpResponse httpResponse);
}
