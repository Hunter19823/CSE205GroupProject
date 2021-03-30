package util;

@FunctionalInterface
public interface IObserver {
    void update(Message<?> message);
}
