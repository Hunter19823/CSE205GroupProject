package shopping;

import util.IObserver;
import util.IPackager;
import util.ISubject;
import util.Message;

import java.util.*;

public class Cart implements IPackager , ISubject {
    private static Cart INSTANCE;
    private final Map<Message.Topic, List<IObserver>> observers = new LinkedHashMap<>();
    private List<Item> contents;

    private Cart()
    {

    }

    public static Cart getInstance(){
        if(INSTANCE == null)
            INSTANCE = new Cart();
        return INSTANCE;
    }

    public void addItem( String uuid, int quantity ) throws ShoppingException
    {

    }

    public void removeItem( Item item ) throws ShoppingException
    {

    }

    public void removeItem( int index ) throws ShoppingException
    {

    }

    public void modify( int index, int quantity) throws ShoppingException
    {

    }

    public void modify( Item item, int quantity) throws ShoppingException
    {

    }

    public void sort( Comparator<? super Item> sortingMethod)
    {
        this.contents.sort(sortingMethod);
    }

    public List<Item> getContents()
    {
        return contents;
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
