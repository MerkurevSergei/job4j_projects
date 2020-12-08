import application.JMS;
import org.junit.Test;
import services.client.Client;
import services.client.ClientImpl;
import services.httpjms.HttpRequestImpl;
import services.httpjms.HttpResponseImpl;
import services.server.Server;
import services.server.http.HttpRequest;
import services.server.http.HttpResponse;
import services.server.session.SessionBrokerSimple;
import views.ViewCommon;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

public class ClientImplServerJMSQueueTest {

    @Test
    public void serverClientExchange()
            throws IOException, InterruptedException, ExecutionException {

        final ExecutorService executorService =
                Executors.newWorkStealingPool();

        final Server server = new Server(new JMS(), new SessionBrokerSimple());
        new Thread(server::start).start();
        Thread.sleep(1000);

        // When Queue Post Success and Post Failed and Exchange not found
        final AtomicInteger postSuccessCounter = new AtomicInteger();
        final AtomicInteger postFailedCounter = new AtomicInteger();
        final AtomicInteger postExchangeNotFoundCounter = new AtomicInteger();
        final LinkedList<Future<?>> clientsQueuePostSuccess = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            Future<?> f = executorService.submit(new ClientQueuePostSuccess(
                    postSuccessCounter,
                    postFailedCounter,
                    postExchangeNotFoundCounter
            ));
            clientsQueuePostSuccess.add(f);
        }

        // Wait post threads
        for (Future<?> client : clientsQueuePostSuccess) {
            client.get();
        }

        // When get message  from  Queue Success and NoContent
        final AtomicInteger qGetSuccessCounter = new AtomicInteger();
        final AtomicInteger qGetNoContentCounter = new AtomicInteger();
        final LinkedList<Future<?>> clientsQueueGet = new LinkedList<>();
        for (int i = 0; i < 150; i++) {
            Future<?> f = executorService
                    .submit(new ClientQueueGet(qGetSuccessCounter, qGetNoContentCounter));
            clientsQueueGet.add(f);
        }

        for (Future<?> client : clientsQueueGet) {
            client.get();
        }

        // Assert
        assertEquals(100, postSuccessCounter.get());
        assertEquals(100, postFailedCounter.get());
        assertEquals(100, postExchangeNotFoundCounter.get());

        assertEquals(100, qGetSuccessCounter.get());
        assertEquals(50, qGetNoContentCounter.get());
        server.close();
    }

    @Test
    public void serverClientExchangeAnyPort() throws InterruptedException, IOException {
        final Server server = new Server(new JMS(), 49002, new SessionBrokerSimple());
        new Thread(server::start).start();
        Thread.sleep(1000);
        final Client client = new ClientImpl();
        client.start("0.0.0.0", 49002);
        Thread.sleep(1000);
        client.close();
        server.close();
    }

    /**
     * Post messages to Queue Success
     */
    private static final class ClientQueuePostSuccess implements Runnable {
        private final HttpRequest httpRequestCorrect;
        private final HttpRequest httpRequestCorruptedHead;
        private final HttpRequest httpRequestCorruptedBody;
        private final AtomicInteger postSuccessCounter;
        private final AtomicInteger postFailedCounter;
        private final AtomicInteger postExchangeNotFoundCounter;

        public ClientQueuePostSuccess(AtomicInteger postSuccessCounter,
                                      AtomicInteger postFailedCounter,
                                      AtomicInteger postExchangeNotFoundCounter) {
            this.postSuccessCounter = postSuccessCounter;
            this.postFailedCounter = postFailedCounter;
            this.postExchangeNotFoundCounter = postExchangeNotFoundCounter;
            httpRequestCorrect = new HttpRequestImpl()
                    .addRequestLine("POST /queue HTTP/1.1")
                    .addContent("{\"queue\" : \"weather\",\"text\" : \"temperature +18 C\"}");
            httpRequestCorruptedHead = new HttpRequestImpl()
                    .addRequestLine("POST /any HTTP/1.1")
                    .addContent("{\"queue\" : \"weather\",\"text\" : \"temperature +18 C\"}");
            httpRequestCorruptedBody = new HttpRequestImpl()
                    .addRequestLine("POST /queue HTTP/1.1")
                    .addContent("\"text\" : \"temperature +18 C\"}");
        }

        @Override
        public void run() {
            final Client client = new ClientImpl();
            try {
                client.start("0.0.0.0", 49001);
                HttpResponse httpResponseCorrect = client.send(httpRequestCorrect);
                HttpResponse httpResponseCorruptedHead = client.send(httpRequestCorruptedHead);
                HttpResponse httpResponseCorruptedBody = client.send(httpRequestCorruptedBody);

                HttpResponse responseModelSuccess = new HttpResponseImpl();
                new ViewCommon().showPostSuccess(responseModelSuccess);
                if (httpResponseCorrect.toString().equals(responseModelSuccess.toString())) {
                    postSuccessCounter.incrementAndGet();
                }

                HttpResponse responseStandardENF = new HttpResponseImpl();
                new ViewCommon().showExchangeTypeNotFound(responseStandardENF);
                if (httpResponseCorruptedHead.toString().equals(responseStandardENF.toString())) {
                    postExchangeNotFoundCounter.incrementAndGet();
                }

                HttpResponse respModelMsgCorrupted = new HttpResponseImpl();
                new ViewCommon().showPostMessageCorrupted(respModelMsgCorrupted);
                if (httpResponseCorruptedBody.toString().equals(respModelMsgCorrupted.toString())) {
                    postFailedCounter.incrementAndGet();
                }

                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get messages from Queue Success and NoContent
     */
    private static final class ClientQueueGet implements Runnable {
        private final AtomicInteger counterSuccess;
        private final AtomicInteger counterNoContent;

        public ClientQueueGet(AtomicInteger counterSuccess, AtomicInteger counterNoContent) {
            this.counterSuccess = counterSuccess;
            this.counterNoContent = counterNoContent;
        }

        @Override
        public void run() {
            final Client client = new ClientImpl();
            try {
                client.start("0.0.0.0", 49001);
                HttpResponse response = client.send(
                        new HttpRequestImpl()
                                .addRequestLine("GET /queue/weather HTTP/1.1")
                );
                if (("HTTP/1.1 200 OK" + lineSeparator()
                        + "{\"queue\":\"weather\",\"text\":\"temperature +18 C\"}")
                        .equals(response.toString())) {
                    counterSuccess.incrementAndGet();
                }
                if (("HTTP/1.1 204 No Content")
                        .equals(response.toString())) {
                    counterNoContent.incrementAndGet();
                }
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}