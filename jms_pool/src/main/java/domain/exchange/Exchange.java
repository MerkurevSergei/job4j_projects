package domain.exchange;

import domain.message.Message;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The {@code Exchange} interface to manage queues and messages.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface Exchange {

    /**
     * Add message to queue.
     *
     * @param queueName of the queue where the message is being added.
     * @param msg       is message that added to queue.
     */
    void addMessage(String queueName, Message msg);

    /**
     * Get message from queue.
     *
     * @param queueName is name queue.
     * @return {@code Message}.
     */
    Message getMessage(String queueName);

    /**
     * Get copy queue from queue by name.
     *
     * @param queueName for getting.
     * @return queue copy from queue by name.
     */
    ConcurrentLinkedQueue<Message> getCopyQueue(String queueName);

    /**
     * Put queue to queue if absent by name.
     *
     * @param queueName is queue name if absent.
     * @param queue     for put.
     */
    void putQueueIfAbsent(String queueName, ConcurrentLinkedQueue<Message> queue);
}
