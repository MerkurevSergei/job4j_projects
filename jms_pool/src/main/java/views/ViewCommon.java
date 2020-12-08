package views;

import services.server.http.HttpResponse;

/**
 * The {@code ViewCommon} implemented {@code View} interface for filling
 * the {@code services.server.http.httpResponse}
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class ViewCommon implements View {

    /**
     * When show post success.
     *
     * @param httpResponse for filling.
     */
    @Override
    public void showPostSuccess(HttpResponse httpResponse) {
        httpResponse.addResponseLine("HTTP/1.1 200 OK");
        httpResponse.addContent("The message was successfully added!");
    }

    /**
     * When show post failed adding message.
     *
     * @param httpResponse for filling.
     */
    @Override
    public void showPostMessageCorrupted(HttpResponse httpResponse) {
        httpResponse.addResponseLine("HTTP/1.1 200 OK");
        httpResponse.addContent("The corrupted message was failed added!");
    }

    /**
     * When show get success.
     *
     * @param httpResponse for filling.
     */
    @Override
    public void showGetSuccess(HttpResponse httpResponse, String msg) {
        httpResponse.addResponseLine("HTTP/1.1 200 OK");
        httpResponse.addContent(msg);
    }

    /**
     * When show get no content.
     *
     * @param httpResponse for filling.
     */
    @Override
    public void showGetNoContent(HttpResponse httpResponse) {
        httpResponse.addResponseLine("HTTP/1.1 204 No Content");
    }

    /**
     * When show exchange type not found,
     * request was been corrupted or invalid.
     *
     * @param httpResponse for filling.
     */
    @Override
    public void showExchangeTypeNotFound(HttpResponse httpResponse) {
        httpResponse.addResponseLine("HTTP/1.1 200 OK");
        httpResponse.addContent("The exchange type is not found!");
    }
}
