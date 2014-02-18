package com.stockticker;

public class User {

    private String userName;
    private String password;
    private boolean loggedIn;
    private int userID;
    private UserInfo userInfo;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    // Getter methods
    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    public int getUserID() {
        return this.userID;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    // Setter methods
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setUserID(int id) {
        this.userID = id;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}