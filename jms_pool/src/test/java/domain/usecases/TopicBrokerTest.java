package domain.usecases;

import domain.message.Message;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class TopicBrokerTest {

    @Test
    public void addMessageGetMessage() {
        final TopicBroker broker = new TopicBroker();
        final Message msg = Mockito.mock(Message.class);
        broker.addMessage("default", msg);
        assertEquals(msg, broker.getMessage("default", "0"));
        assertNull(broker.getMessage("NoDefine", "0"));
    }

    @Test
    public void getMessageFromThreads() throws InterruptedException {
        final TopicBroker broker = new TopicBroker();
        for (int i = 0; i < 100; i++) {
            broker.addMessage("default", Mockito.mock(Message.class));
        }
        final AtomicInteger count = new AtomicInteger();
        final ArrayList<Thread> threads = new ArrayList<>();
        final CyclicBarrier cb = new CyclicBarrier(5);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            final Thread t = new Thread(() -> {
                try {
                    cb.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 100; j++) {
                    final Message msg = broker.getMessage("default", String.valueOf(finalI));
                    if (msg != null) {
                        count.incrementAndGet();
                    }
                }
            });
            threads.add(t);
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        assertEquals(500, count.get());
    }
}