package util;

import java.util.List;

public interface ISubscriber {
    List<Message<?>> getSubscriberMessages();
    void addSubscriberMessage(Message<?> message);
    void addSubscriber(Message.Topic topic, PubSubService pubSubService);
    void unSubscribe(Message.Topic topic, PubSubService pubSubService);
    List<Message<?>> getMessagesByTopic(Message.Topic topic);
    default void printMessages()
    {
        for(Message message : getSubscriberMessages()){
            System.out.println("Message Topic -> "+ message.Topic + " : " + message.CONTENTS);
        }
    }
}
