@FunctionalInterface
public interface IObserver<C,E> {
    void onUpdate(C subject, E data);
}
