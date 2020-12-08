package services.server.session;

/**
 * The {@code SessionBroker} interface for manage sessions.
 * Take sessions and remove id from sessions pool, when
 * release session id come back to sessions pool.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface SessionBroker {

    /**
     * Get session id and remove from sessions pool.
     *
     * @return session id.
     */
    Integer takeSession();

    /**
     * Come back session id to sessions pool,
     * if id absent, or nothing.
     *
     * @param id session.
     */
    void releaseSession(Integer id);
}
