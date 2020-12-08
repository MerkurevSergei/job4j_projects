package services.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.httpjms.HttpResponseImpl;
import services.server.http.HttpRequest;
import services.server.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The {@code ClientImpl} implements {@code Client} interface.
 * Used {@code HttpRequest} and {@code HttpRequest} from
 * package {@code services.server.http}.
 * Connect to server, send and receive messages.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class ClientImpl implements Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Start client and connect to server ip:port.
     *
     * @param ip   address.
     * @param port listening.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void start(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     * Send request to server and receive response.
     *
     * @param httpRequest request to send.
     * @return HttpResponse.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public HttpResponse send(HttpRequest httpRequest) throws IOException {
        final Logger logger = LoggerFactory.getLogger(ClientImpl.class);
        logger.debug("CLIENT: Send request to server");
        out.println(httpRequest.toString());
        out.println();
        out.println();
        HttpResponse httpResponse = new HttpResponseImpl();
        try {
            logger.debug("CLIENT: Wait response from server...");
            httpResponse.addResponseLine(readLine());
            int countEmptyLine = 0;
            while (countEmptyLine < 2) {
                String line = readLine();
                if (!line.isBlank()) {
                    httpResponse.addContent(line);
                }
                countEmptyLine = line.isBlank() ? ++countEmptyLine : 0;
            }
            logger.debug("CLIENT: response received!");
        } catch (IllegalStateException e) {
            logger.debug("CLIENT: FAIL RECEIVE RESPONSE!");
            httpResponse = new HttpResponseImpl();
            httpResponse.addResponseLine("HTTP/1.1 400 Bad Response");
            httpResponse.addContent(e.toString());
        }
        return httpResponse;
    }

    /**
     * Close server.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void close() throws IOException {
        clientSocket.close();
    }

    private String readLine() throws IOException {
        String line = in.readLine();
        if (line == null) {
            clientSocket.close();
            throw new IOException("Connection closed!");
        }
        return line.trim();
    }
}
