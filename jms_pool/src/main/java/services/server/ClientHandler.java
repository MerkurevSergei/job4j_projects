package services.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.httpjms.HttpRequestImpl;
import services.httpjms.HttpResponseImpl;
import services.server.http.HttpRequest;
import services.server.http.HttpResponse;
import services.server.session.SessionBroker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * The {@code ClientHandler} for {@code Server}.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class ClientHandler implements Runnable {
    private final Socket client;
    private final BufferedReader in;
    private final PrintWriter out;
    private final Application application;
    private final SessionBroker sessionBroker;

    /**
     * @param client init from {@code Server}
     * @param app    init from {@code Server}
     * @param broker init from {@code Server}
     * @throws IOException if I/O exception occurred
     */
    public ClientHandler(Socket client, Application app, SessionBroker broker) throws IOException {
        this.client = client;
        this.in = new BufferedReader(new InputStreamReader(client.getInputStream(), UTF_8));
        this.out = new PrintWriter(client.getOutputStream(), true);
        this.application = app;
        this.sessionBroker = broker;
    }

    /**
     * The {@code Runnable} interface implementation.
     * Processes the request from the client and sends a response.
     */
    @Override
    public void run() {
        final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
        logger.debug("SERVER: The client connected.");
        int id;
        try {
            id = sessionBroker.takeSession();
        } catch (NullPointerException e) {
            logger.debug("SERVER: Too many sessions");
            return;
        }
        while (!client.isClosed()) {
            HttpRequest httpRequest = new HttpRequestImpl(String.valueOf(id));
            HttpResponse httpResponse = new HttpResponseImpl();
            try {
                logger.debug("SERVER: Wait request from client...");
                receiveHttpRequest(httpRequest);
                logger.debug("SERVER: request received! Prepare response to client");
                application.execute(httpRequest, httpResponse);
                logger.debug("SERVER: response prepared! Send to client: HTTP/1.1 200 OK");
            } catch (IllegalStateException e) {
                logger.debug("SERVER: FAIL RECEIVE! Send to client: HTTP/1.1 400 Bad Request");
                httpResponse.addResponseLine("HTTP/1.1 400 Bad Request");
                httpResponse.addContent(e.toString());
            } catch (IOException e) {
                logger.debug(
                        "I/O exception when trying to read the request:" + e.toString());
                break;
            }
            sendHttpResponse(httpResponse);
        }
        sessionBroker.releaseSession(id);
    }

    /**
     * Build instance {@code HttpRequest} from client socket input stream.
     *
     * @param httpRequest {@code HttpRequest}.
     */
    private void receiveHttpRequest(HttpRequest httpRequest)
            throws IOException, IllegalStateException {
        httpRequest.addRequestLine(readLine());
        int countEmptyLine = 0;
        while (countEmptyLine < 2) {
            String line = readLine();
            if (!line.isBlank()) {
                httpRequest.addContent(line);
            }
            countEmptyLine = line.isBlank() ? ++countEmptyLine : 0;
        }
    }

    /**
     * Send {@code HttpResponse} from {@code Application} to client output stream.
     *
     * @param httpResponse {@code HttpResponse} from {@code Application}.
     */
    private void sendHttpResponse(HttpResponse httpResponse) {
        out.println(httpResponse.toString());
        out.println();
        out.println();
    }

    /**
     * Read line from client socket stream.
     *
     * @return string line.
     * @throws IOException if an I/O error occurs.
     */
    private String readLine() throws IOException {
        String line = in.readLine();
        if (line == null) {
            client.close();
            throw new IOException("Connection closed!");
        }
        return line.trim();
    }
}
