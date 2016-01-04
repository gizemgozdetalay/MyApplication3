package com.example.gizem.myapplication;

/**
 * Created by Gizem on 03.01.2016.
 */
public class Contact {

    String name;
    String number;

    public  Contact()
    {

    }

    public Contact(String name,String number)
    {
        this.name=name;
        this.number=number;
    }

    public String getName()
    {
        return name;
    }

    public String getNumber()
    {
        return number;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public void setNumber(String number)
    {
        this.number=number;
    }
}
