package shopping;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import util.IObserver;
import util.IPackager;
import util.ISubject;
import util.Message;

import java.util.*;

public class Item implements IPackager, ISubject {
    private static final Logger LOGGER = LogManager.getLogger(Item.class);
    private static final Map<String,Item> CACHED_ITEMS = new HashMap<>();
    private final Map<Message.Topic, List<IObserver>> observers = new LinkedHashMap<>();
    private String uuid = null;
    private String name;
    private String description;
    private int quantity;
    private int price;

    private Item()
    {

    }

    public String getUuid()
    {
        return this.uuid;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }

    public int getQuantity()
    {
        return this.quantity;
    }

    public int getPrice()
    {
        return this.price;
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
