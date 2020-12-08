package domain.message;

import org.junit.Test;

import static org.junit.Assert.*;

public class QueueMessageTest {

    @Test
    public void getHeader() {
        final QueueMessage msg = new QueueMessage("weather", "18");
        assertEquals("weather", msg.getHeader());
    }

    @Test
    public void getBody() {
        final QueueMessage msg = new QueueMessage("weather", "18");
        assertEquals("18", msg.getBody());
    }
}