package domain.message;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * The {@code TopicMessage} implemented {@code Message} interface.
 * Header convert to "topic" for JSON. This message is immutable.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public final class TopicMessage implements Message {
    private final String topic;
    private final String text;

    /**
     * @param head init.
     * @param body init.
     */
    @JsonCreator
    public TopicMessage(@NotNull @JsonProperty(required = true, value = "topic") String head,
                        @NotNull @JsonProperty(required = true, value = "text") String body) {
        this.topic = Optional.of(head).get();
        this.text = Optional.of(body).get();
    }

    /**
     * @return message header.
     */
    @Override
    public @NotNull String getHeader() {
        return topic;
    }

    /**
     * @return message body.
     */
    @Override
    public @NotNull String getBody() {
        return text;
    }
}
