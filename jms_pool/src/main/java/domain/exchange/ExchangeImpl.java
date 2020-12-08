package domain.exchange;

import domain.message.Message;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * The {@code ExchangeImpl} implemented {@code Exchange} interface
 * to manage queues and messages. This exchange has set of queues for
 * addMessage and getMessage.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class ExchangeImpl implements Exchange {
    private final ConcurrentMap<String, ConcurrentLinkedQueue<Message>> queues;

    /**
     * Initialize.
     */
    public ExchangeImpl() {
        this.queues = new ConcurrentHashMap<>();
    }

    /**
     * Add message to queue.
     *
     * @param queueName of the queue where the message is being added.
     * @param msg       is message that added to queue.
     */
    @Override
    public void addMessage(String queueName, Message msg) {
        queues.putIfAbsent(queueName, new ConcurrentLinkedQueue<>());
        if (msg != null) {
            queues.get(queueName).add(msg);
        }
    }

    /**
     * Get message from queue.
     *
     * @param queueName is name queue.
     * @return {@code Message}.
     */
    @Override
    public Message getMessage(String queueName) {
        return queues.getOrDefault(queueName, new ConcurrentLinkedQueue<>()).poll();
    }

    /**
     * Get copy queue from queue by name.
     *
     * @param queueName for getting.
     * @return queue copy from queue by name.
     */
    @Override
    public ConcurrentLinkedQueue<Message> getCopyQueue(String queueName) {
        final ConcurrentLinkedQueue<Message> q = queues.get(queueName);
        return q == null ? new ConcurrentLinkedQueue<>() : new ConcurrentLinkedQueue<>(q);
    }

    /**
     * Put queue to queue if absent by name.
     *
     * @param queueName is queue name if absent.
     * @param queue     for put.
     */
    @Override
    public void putQueueIfAbsent(String queueName, ConcurrentLinkedQueue<Message> queue) {
        queues.putIfAbsent(queueName, queue);
    }
}
