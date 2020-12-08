package services.server.session;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;

public class SessionBrokerSimpleTest {

    @Test
    public void takeSession() throws ExecutionException, InterruptedException {
        final ArrayList<Integer> actual = new ArrayList<>();
        final SessionBrokerSimple broker = new SessionBrokerSimple();
        final int cores = Runtime.getRuntime().availableProcessors();
        final ExecutorService executorService = Executors.newFixedThreadPool(cores);
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 10; j++) {
                    actual.add(broker.takeSession());
                }
            }).get();
        }
        Collections.sort(actual);

        final Integer[] expected = IntStream.range(0, 1000).boxed().toArray(Integer[]::new);
        assertArrayEquals(expected, actual.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void takeSessionWhenBrokerNoMoreSessions() {
        final SessionBrokerSimple broker = new SessionBrokerSimple();
        for (int j = 0; j < 1001; j++) {
            broker.takeSession();
        }
    }

    @Test
    public void releaseSession() {
        final ArrayList<Integer> sessions = new ArrayList<>();
        final SessionBrokerSimple broker = new SessionBrokerSimple();
        for (int j = 0; j < 1000; j++) {
            sessions.add(broker.takeSession());
        }
        for (Integer i : sessions) {
            broker.releaseSession(i);
        }
        sessions.clear();
        for (int j = 0; j < 1000; j++) {
            sessions.add(broker.takeSession());
        }
        Collections.sort(sessions);
        final Integer[] expected = IntStream.range(0, 1000).boxed().toArray(Integer[]::new);
        assertArrayEquals(expected, sessions.toArray());
    }
}