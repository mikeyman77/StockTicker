package com.stockticker;

public class User {

    private String userName;
    private String password;
    private boolean loggedIn;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

}