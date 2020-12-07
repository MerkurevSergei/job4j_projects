package services;

import java.io.IOException;

/**
 * The Game logger interface.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface Logger extends AutoCloseable {

    /**
     * @param log file for log
     * @throws IOException if write throw
     */
    void write(String log) throws IOException;
}
