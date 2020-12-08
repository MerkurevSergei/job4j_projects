package domain.exchange;

import domain.message.Message;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class ExchangeImplTest {

    @Test
    public void addMessageGetMessage() {
        final ExchangeImpl exchange = new ExchangeImpl();
        final Message msg = Mockito.mock(Message.class);
        exchange.addMessage("default", msg);
        assertEquals(msg, exchange.getMessage("default"));
        assertNull(exchange.getMessage("NoDefine"));
    }

    @Test
    public void getCopyQueueWhenExist() {
        final ExchangeImpl exchange = new ExchangeImpl();
        final Message msg = Mockito.mock(Message.class);
        exchange.addMessage("default", msg);
        assertFalse(exchange.getCopyQueue("default").isEmpty());
    }

    @Test
    public void getCopyQueueWhenNotExist() {
        final ExchangeImpl exchange = new ExchangeImpl();
        assertTrue(exchange.getCopyQueue("default").isEmpty());
    }
}