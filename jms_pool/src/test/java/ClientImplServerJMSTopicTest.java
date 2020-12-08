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

public class ClientImplServerJMSTopicTest {

    @Test
    public void serverClientExchange()
            throws IOException, InterruptedException, ExecutionException {

        final ExecutorService executorService =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        final Server server = new Server(new JMS(), new SessionBrokerSimple());
        new Thread(server::start).start();
        Thread.sleep(1000);

        // When add Topic Post Success, Failed, NoExchange
        final AtomicInteger postSuccessCounter = new AtomicInteger();
        final AtomicInteger postFailedCounter = new AtomicInteger();
        final AtomicInteger postExchangeNotFoundCounter = new AtomicInteger();
        final LinkedList<Future<?>> clientsTopicPostSuccess = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            Future<?> f = executorService.submit(new ClientTopicPostSuccess(
                    postSuccessCounter,
                    postFailedCounter,
                    postExchangeNotFoundCounter));
            clientsTopicPostSuccess.add(f);
        }
        for (Future<?> client : clientsTopicPostSuccess) {
            client.get();
        }

        // When get message  from  Topic Success and NoContent
        final AtomicInteger tGetSuccessCounter = new AtomicInteger();
        final AtomicInteger tGetNoContentCounter = new AtomicInteger();
        final Future<?> submit0 = executorService
                .submit(new ClientTopicGet(tGetSuccessCounter, tGetNoContentCounter));
        final Future<?> submit1 = executorService
                .submit(new ClientTopicGet(tGetSuccessCounter, tGetNoContentCounter));
        final Future<?> submit2 = executorService
                .submit(new ClientTopicGet(tGetSuccessCounter, tGetNoContentCounter));
        final Future<?> submit3 = executorService
                .submit(new ClientTopicGet(tGetSuccessCounter, tGetNoContentCounter));
        final Future<?> submit4 = executorService
                .submit(new ClientTopicGet(tGetSuccessCounter, tGetNoContentCounter));
        submit0.get();
        submit1.get();
        submit2.get();
        submit3.get();
        submit4.get();

        // Assert
        assertEquals(100, postSuccessCounter.get());
        assertEquals(100, postFailedCounter.get());
        assertEquals(100, postExchangeNotFoundCounter.get());

        assertEquals(500, tGetSuccessCounter.get());
        assertEquals(250, tGetNoContentCounter.get());
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
     * Post messages to Topic Success
     */
    private static final class ClientTopicPostSuccess implements Runnable {
        private final AtomicInteger successCounter;
        private final AtomicInteger failedCounter;
        private final AtomicInteger exchangeNotFoundCounter;
        private final HttpRequest httpRequestCorrect;
        private final HttpRequest httpRequestCorruptedHead;
        private final HttpRequest httpRequestCorruptedBody;

        public ClientTopicPostSuccess(
                AtomicInteger successCounter,
                AtomicInteger failedCounter,
                AtomicInteger exchangeNotFoundCounter) {
            this.successCounter = successCounter;
            this.failedCounter = failedCounter;
            this.exchangeNotFoundCounter = exchangeNotFoundCounter;
            httpRequestCorrect = new HttpRequestImpl()
                    .addRequestLine("POST /topic HTTP/1.1")
                    .addContent("{\"topic\" : \"weather\",\"text\" : \"temperature +18 C\"}");
            httpRequestCorruptedHead = new HttpRequestImpl()
                    .addRequestLine("POST /any HTTP/1.1")
                    .addContent("{\"topic\" : \"weather\",\"text\" : \"temperature +18 C\"}");
            httpRequestCorruptedBody = new HttpRequestImpl()
                    .addRequestLine("POST /topic HTTP/1.1")
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
                    successCounter.incrementAndGet();
                }

                HttpResponse responseStandardENF = new HttpResponseImpl();
                new ViewCommon().showExchangeTypeNotFound(responseStandardENF);
                if (httpResponseCorruptedHead.toString().equals(responseStandardENF.toString())) {
                    exchangeNotFoundCounter.incrementAndGet();
                }

                HttpResponse respModelMsgCorrupted = new HttpResponseImpl();
                new ViewCommon().showPostMessageCorrupted(respModelMsgCorrupted);
                if (httpResponseCorruptedBody.toString().equals(respModelMsgCorrupted.toString())) {
                    failedCounter.incrementAndGet();
                }

                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get messages from Topic Success and NoContent
     */
    private static final class ClientTopicGet implements Runnable {
        private final AtomicInteger counterSuccess;
        private final AtomicInteger counterNoContent;

        public ClientTopicGet(AtomicInteger counterSuccess, AtomicInteger counterNoContent) {
            this.counterSuccess = counterSuccess;
            this.counterNoContent = counterNoContent;
        }

        @Override
        public void run() {
            try {
                final Client client = new ClientImpl();
                client.start("0.0.0.0", 49001);
                for (int i = 0; i < 150; i++) {
                    HttpResponse response = client.send(
                            new HttpRequestImpl()
                                    .addRequestLine("GET /topic/weather HTTP/1.1")
                    );
                    if (("HTTP/1.1 200 OK" + lineSeparator()
                            + "{\"topic\":\"weather\",\"text\":\"temperature +18 C\"}")
                            .equals(response.toString())) {
                        counterSuccess.incrementAndGet();
                    } else {
                        counterNoContent.incrementAndGet();
                    }
                }
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}