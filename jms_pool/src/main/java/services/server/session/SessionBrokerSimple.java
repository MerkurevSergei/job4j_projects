package services.server.session;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The {@code SessionBroker} interface for manage sessions.
 * Take sessions and remove id from sessions pool, when
 * release session id come back to sessions pool.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class SessionBrokerSimple implements SessionBroker {
    private final ConcurrentLinkedQueue<Integer> sessionIds;

    /**
     * Constructor.
     */
    public SessionBrokerSimple() {
        this.sessionIds = IntStream
                .range(0, 1000)
                .boxed()
                .collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
    }

    /**
     * Get session id and remove from sessions pool.
     *
     * @return session id.
     */
    @Override
    public Integer takeSession() {
        return Objects.requireNonNull(sessionIds.poll());
    }

    /**
     * Come back session id to sessions pool,
     * if id absent, or nothing.
     *
     * @param id session.
     */
    @Override
    public void releaseSession(Integer id) {
        if (!sessionIds.contains(id)) {
            sessionIds.add(id);
        }
    }
}
