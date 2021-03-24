package util;

import java.util.List;

public interface ISubject {
    boolean attach( Message.Topic topic, IObserver observer);
    boolean detach( Message.Topic topic, IObserver observer);
    List<IObserver> getObservers( Message.Topic topic );
    void notifyUpdate(Message message);
}
