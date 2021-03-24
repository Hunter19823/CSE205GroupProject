package shopping;

public class ShoppingException extends Exception {
    public ShoppingException()
    {

    }
    public ShoppingException( String message)
    {
        super(message);
    }
    public ShoppingException( String message, Throwable cause)
    {
        super(message,cause);
    }

    public ShoppingException( Throwable cause)
    {
        super(cause);
    }
}
