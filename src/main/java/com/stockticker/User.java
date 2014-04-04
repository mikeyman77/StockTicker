package com.stockticker;

/**
 * Defines the user data.
 * 
 * @author Michael Grissom
 */
public class User {

    /**
     * The username
     */
    private String userName;

    /**
     * The user's password
     */
    private String password;

    /**
     * User's login status
     */
    private boolean loggedIn;

    /**
     * The internal user id
     */
    private int userID;

    /**
     * A UserInfo instance that defines additional user data
     */
    private UserInfo userInfo;

    /**
     * Constructs a User object
     */
    public User() { }

    /**
     * Constructs a User object
     * 
     * @param userName username of the user
     * @param password password of the user
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Retrieves the username.
     *
     * @return the username
     */
        public String getUserName() {
        return this.userName;
    }

    /**
     * Retrieves the password.
     *
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Retrieves user's login status.
     *
     * @return user's login status
     */
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    /**
     * Retrieves the user id.
     *
     * @return the user id
     */
    public int getUserID() {
        return this.userID;
    }

    /**
     * Retrieves the UserInfo instance.
     *
     * @return the UserInfo instance
     */
    public UserInfo getUserInfo() {
        return this.userInfo;
    }
    
    /**
     * Sets the username.
     *
     * @param username the username
     */
    public void setUserName(String username) {
        this.userName = username;
    }

    /**
     * Sets the user's password.
     *
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Sets the login status.
     *
     * @param loggedIn the user's login status
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Sets the user id.
     *
     * @param id the user id
     */
    public void setUserID(int id) {
        this.userID = id;
    }

    /**
     * Sets the UserInfo instance.
     *
     * @param userInfo the UserInfo data
     */
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}