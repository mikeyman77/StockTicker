package com.stockticker;

public class User {

    private String userName;
    private boolean loggedIn;

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

}