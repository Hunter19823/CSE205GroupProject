package example;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(AccessLevel.PACKAGE)
@Embeddable
public class Password implements CharSequence{

    private final String password;
    private transient boolean encrypted;

    public Password()
    {
        this.password = null;
        this.encrypted = true;
    }

    public static Password raw( String password)
    {
        return new Password(password,false);
    }

    public static Password encrypted(String password)
    {
        return new Password(password, true);
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
