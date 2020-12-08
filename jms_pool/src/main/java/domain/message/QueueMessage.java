package domain.message;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * The {@code QueueMessage} implemented {@code Message} interface.
 * Header convert to "queue" for JSON. This message is immutable.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public final class QueueMessage implements Message {
    private final String queue;
    private final String text;

    /**
     * @param head init.
     * @param body init.
     */
    @JsonCreator
    public QueueMessage(@NotNull @JsonProperty(required = true, value = "queue") String head,
                        @NotNull @JsonProperty(required = true, value = "text") String body) {
        this.queue = Optional.of(head).get();
        this.text = Optional.of(body).get();
    }

    /**
     * @return message header.
     */
    @Override
    public @NotNull String getHeader() {
        return queue;
    }

    /**
     * @return message body.
     */
    @Override
    public @NotNull String getBody() {
        return text;
    }
}
