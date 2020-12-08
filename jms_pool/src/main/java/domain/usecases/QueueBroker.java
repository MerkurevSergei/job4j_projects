package domain.usecases;

import domain.message.Message;
import domain.exchange.Exchange;
import domain.exchange.ExchangeImpl;

/**
 * The {@code QueueBroker} use case when a message is added
 * and extracted for the single queue.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class QueueBroker {
    private final Exchange exchange = new ExchangeImpl();

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
     * @param queueName is name queue from queue.
     * @return {@code Message}.
     */
    public Message getMessage(String queueName) {
        return exchange.getMessage(queueName);
    }
}
