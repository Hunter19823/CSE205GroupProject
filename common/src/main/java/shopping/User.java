package shopping;

import util.IObserver;
import util.IPackager;
import util.ISubject;
import util.Message;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class User implements IPackager, ISubject {
    private static User INSTANCE;
    private final Map<Message.Topic, List<IObserver>> observers = new LinkedHashMap<>();
    private String uuid;
    private String username;
    private static String token;

    private User()
    {

    }

    public static User getInstance(){
        if(INSTANCE == null)
            INSTANCE = new User();
        return INSTANCE;
    }

    public String getUuid()
    {
        return uuid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
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
        return observers.getOrDefault(topic, new LinkedList<>());
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
