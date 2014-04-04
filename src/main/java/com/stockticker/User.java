package com.stockticker;

/**
 * This is a data class for the user.
 * 
 * @author Michael Grissom
 */
public class User {

    private String userName;
    private String password;
    private boolean loggedIn;
    private int userID;
    private UserInfo userInfo;

    /**
     * Default constructor
     */
    public User() { }

    /**
     * Designated constructor with username and password.
     * 
     * @param userName username of the user
     * @param password password of the user
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Returns the username.
     */
        public String getUserName() {
        return this.userName;
    }

    /**
     * Returns the password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns if the user is logged in.
     */
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    /**
     * Returns the user id.
     */
    public int getUserID() {
        return this.userID;
    }

    /**
     * Returns the user information.
     */
    public UserInfo getUserInfo() {
        return this.userInfo;
    }
    
    /**
     * Set the username.
     */
        public void setUserName(String username) {
        this.userName = username;
    }

    /**
     * Set the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Set the logged in status.
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Set the user id.
     */
    public void setUserID(int id) {
        this.userID = id;
    }

    /**
     * Set the user information.
     */
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}