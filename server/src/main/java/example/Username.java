package example;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;

@EqualsAndHashCode
@Embeddable
public class Username {
    private final String username;
    public Username()
    {
        this.username = null;
    }

    public Username( String username)
    {
        if(!username.matches("(?!^[@\\.\\_\\-\\d])(?!.*[@\\.\\_\\-]$)(?!.*[@\\.\\_\\-]{2,})(?!.*@+(?![a-zA-Z0-9-]*\\.+[a-zA-Z0-9]{2,4}))^([a-zA-Z0-9\\-\\_@.]{4,64})+$")){
            throw new IllegalArgumentException("Invalid Username!");
        }
        this.username = username;
    }

    @Override
    public String toString()
    {
        return username;
    }
}
