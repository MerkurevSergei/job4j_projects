package domain.usecases;

import domain.message.Message;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class QueueBrokerTest {

    @Test
    public void addMessageGetMessage() {
        final QueueBroker broker = new QueueBroker();
        final Message msg = Mockito.mock(Message.class);
        broker.addMessage("default", msg);
        assertEquals(msg, broker.getMessage("default"));
        assertNull(broker.getMessage("NoDefine"));
    }

    @Test
    public void getMessageFromManyThread() throws InterruptedException {
        final QueueBroker broker = new QueueBroker();
        for (int i = 0; i < 100; i++) {
            broker.addMessage("default", Mockito.mock(Message.class));
        }
        final AtomicInteger count = new AtomicInteger();
        final ArrayList<Thread> threads = new ArrayList<>();
        final CyclicBarrier cb = new CyclicBarrier(5);
        for (int i = 0; i < 5; i++) {
            final Thread t = new Thread(() -> {
                try {
                    cb.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 100; j++) {
                    final Message msg = broker.getMessage("default");
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
        assertEquals(100, count.get());
    }
}