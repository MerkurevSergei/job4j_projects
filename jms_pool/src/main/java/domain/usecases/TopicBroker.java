package domain.usecases;

import domain.exchange.Exchange;
import domain.exchange.ExchangeImpl;
import domain.message.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@code TopicBroker} use case when a message is send
 * in single exchange, but extracted from a local exchange
 * is created, one for each connection id.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class TopicBroker {
    private final Exchange exchange = new ExchangeImpl();
    private final Map<String, Exchange> localExchanges = new ConcurrentHashMap<>();

    /**
     * Send message to exchange.
     *
     * @param queueName of the queue where the message is being added.
     * @param msg       is message that added to queue.
     */
    public void addMessage(String queueName, Message msg) {
        exchange.addMessage(queueName, msg);
    }

    /**
     * Get message from exchange.
     *
     * @param queueName is name queue from exchange.
     * @return {@code Message}.
     */
    public Message getMessage(String queueName, String sessionId) {
        localExchanges.putIfAbsent(sessionId, new ExchangeImpl());
        final Exchange localExchange = localExchanges.get(sessionId);
        localExchange.putQueueIfAbsent(queueName, exchange.getCopyQueue(queueName));
        return localExchange.getMessage(queueName);
    }
}
