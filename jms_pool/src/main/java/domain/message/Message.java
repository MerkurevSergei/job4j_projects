package domain.message;

import org.jetbrains.annotations.NotNull;

/**
 * The {@code Message} interface.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface Message {

    /**
     * @return message header.
     */
    @NotNull String getHeader();

    /**
     * @return message body.
     */
    @NotNull String getBody();
}
