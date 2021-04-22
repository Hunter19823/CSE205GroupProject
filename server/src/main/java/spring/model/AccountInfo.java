package spring.model;

import spring.entity.Account;

public class AccountInfo {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String address;

    public AccountInfo( Account account)
    {
        this.username = account.getUsername();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.email = account.getEmail();
        this.address = account.getAddress();
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }
}
