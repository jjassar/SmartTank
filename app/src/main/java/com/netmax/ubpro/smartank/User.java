package com.netmax.ubpro.smartank;

/**
 * Created by preeti on 16/7/16.
 */
public class User {


    public String username;
    public String email;


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }


    public User() {
        //Must
    }
    public User(String u , String e) {
        //Must

        username = u;
        email = e;
    }




    public User(String username, String email,String password)
    {
        this.username = username;
        this.email = email;

    }

}
