package util;


import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public abstract class Subscriber implements ISubscriber {
    private List<Message> messageList = new LinkedList<>();

    @Override
    public List<Message> getSubscriberMessages()
    {
        return this.messageList;
    }

    @Override
    public void addSubscriberMessage( Message message )
    {
        this.messageList.add(message);
    }

    @Override
    public void addSubscriber( Message.Topic topic, PubSubService pubSubService )
    {
        pubSubService.addSubscriber(topic,this);
    }

    @Override
    public void unSubscribe( Message.Topic topic, PubSubService pubSubService )
    {
        pubSubService.removeSubscriber(topic,this);
    }

    @Override
    public List<Message> getMessagesByTopic( Message.Topic topic )
    {
        ListIterator<Message> listIterator = messageList.listIterator();
        List<Message> output = new LinkedList<>();
        Message message;
        while(listIterator.hasNext()) {
            message = listIterator.next();
            if (message.Topic.equals(topic))
                listIterator.remove();
            output.add(message);
        }
        return output;
    }


}
