package example;

import javax.persistence.Embeddable;

@Embeddable
public class Password implements CharSequence{

    private final String password;
    private transient boolean encrypted;

    Password()
    {
        this.password = null;
        this.encrypted = true;
    }

    private Password(String password, boolean encrypted)
    {
        this.password = password;
        this.encrypted = encrypted;
    }

    public static Password raw( String password)
    {
        return new Password(password,false);
    }

    static Password encrypted(String password)
    {
        return new Password(password, true);
    }

    public boolean isEncrypted()
    {
        return encrypted;
    }

    @Override
    public int length()
    {
        return this.password.length();
    }

    @Override
    public char charAt( int index )
    {
        return this.password.charAt(index);
    }

    @Override
    public CharSequence subSequence( int start, int end )
    {
        return this.password.subSequence(start,end);
    }

    public String toString()
    {
        return encrypted ? password : "*******";
    }
}
