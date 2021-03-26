package util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;

public class PubSubService {
    private static final Logger LOGGER = LogManager.getLogger(PubSubService.class);
    private final Map<Message.Topic, Set<ISubscriber>> topicMap = new HashMap<>();
    private Queue<Message> messageQueue = new LinkedList<>();

    public void addMessageToQueue(Message message)
    {
        messageQueue.add(message);
    }

    public boolean addSubscriber( Message.Topic topic, ISubscriber subscriber)
    {
        if(!topicMap.containsKey(topic))
            topicMap.put(topic,new HashSet<>());
        return topicMap.get(topic).add(subscriber);
    }

    public boolean removeSubscriber( Message.Topic topic, ISubscriber subscriber)
    {
        if(topicMap.containsKey(topic))
            return topicMap.get(topic).remove(subscriber);
        return false;
    }

    private Set<ISubscriber> getSubscribers( Message.Topic topic)
    {
        if(!topicMap.containsKey(topic))
            topicMap.put(topic,new HashSet<>());
        return topicMap.get(topic);
    }

    public void broadcast()
    {
        if(messageQueue.isEmpty())
        {
            System.out.println("No messages to broadcast.");
        }else{
            Message message;
            while(!messageQueue.isEmpty()){
                message = messageQueue.remove();
                for(ISubscriber subscriber : getSubscribers(message.Topic)){
                    subscriber.addSubscriberMessage(message);
                }
            }
        }
    }


}
