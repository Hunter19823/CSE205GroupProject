package util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Message<PAY_LOAD> {
    private static final Logger LOGGER = LogManager.getLogger(Message.class);
    public interface Topic { }

    public final Topic Topic;
    public final PAY_LOAD CONTENTS;
    public Message( Topic topic, PAY_LOAD contents)
    {
        this.Topic = topic;
        this.CONTENTS = contents;
    }
}
