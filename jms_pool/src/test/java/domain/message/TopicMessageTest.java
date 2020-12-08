package domain.message;

import org.junit.Test;

import static org.junit.Assert.*;

public class TopicMessageTest {

    @Test
    public void getHeader() {
        final TopicMessage msg = new TopicMessage("weather", "18");
        assertEquals("weather", msg.getHeader());
    }

    @Test
    public void getBody() {
        final TopicMessage msg = new TopicMessage("weather", "18");
        assertEquals("18", msg.getBody());
    }
}