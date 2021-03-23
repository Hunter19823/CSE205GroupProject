public interface Subject<C,D> {

    void attach( IObserver<C,D> IObserver );
    void detach( IObserver<C,D> IObserver );
    void notifyUpdate(D message);
}
