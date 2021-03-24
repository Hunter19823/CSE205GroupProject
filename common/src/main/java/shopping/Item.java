package shopping;

import util.IObserver;
import util.IPackager;
import util.ISubject;
import util.Message;

import java.util.*;

public class Item implements IPackager, ISubject {
    private static final Map<String,Item> CACHED_ITEMS = new HashMap<>();
    private final Map<Message.Topic, List<IObserver>> observers = new LinkedHashMap<>();
    private String uuid;
    private String name;
    private String description;
    private int quantity;

    private Item()
    {

    }

    public String getUuid()
    {
        return uuid;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public int getQuantity()
    {
        return quantity;
    }

    @Override
    public String pack()
    {
        return null;
    }

    @Override
    public void unpack( String string )
    {

    }

    @Override
    public boolean attach( Message.Topic topic, IObserver observer )
    {
        return getObservers(topic).add(observer);
    }

    @Override
    public boolean detach( Message.Topic topic, IObserver observer )
    {
        return getObservers(topic).remove(observer);
    }

    @Override
    public List<IObserver> getObservers( Message.Topic topic )
    {
        return observers.getOrDefault(topic,new LinkedList<>());
    }

    @Override
    public void notifyUpdate( Message message )
    {
        // TODO add exception handling to prevent domino effect.
        getObservers(message.Topic).parallelStream().forEachOrdered(observer ->
                observer.update(message)
        );
    }
}
