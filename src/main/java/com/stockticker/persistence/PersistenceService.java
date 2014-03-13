package com.stockticker.persistence;

import java.util.List;
import com.stockticker.User;
import com.stockticker.UserInfo;

public interface PersistenceService {

    /**
     * Starts the Persistence Service which performs necessary
     * initialization.
     *
     * @throws PersistenceServiceException
     */
    public void start() throws PersistenceServiceException;


    /**
     * Returns a List of stock symbols being tracked by a specific user
     *
     * @param username name of user
     * @return list of stock symbols
     */
    public List<String> getTrackedStocks(String username);

    /**
     * Sets whether or not a stock is to be tracked
     *
     * @param username name of user
     * @param stock    stock symbol to track
     * @param track    true to track, false to un-track
     * @return true or false
     */
    public boolean trackStock(String username, String stock, boolean track);

    /**
     * Tests if a stock is being tracked for a specific user
     *
     * @param username name of user
     * @param stock    stock symbol to check
     * @return         true if tracked, false otherwise
     */
    public boolean isStockTracked(String username, String stock);

    /**
     * Checks if a user exists in the database
     *
     * @param username the name of the user
     * @return true if exists, false otherwise
     */
    public boolean userExists(String username) throws PersistenceServiceException;

    /**
     * Creates a new user in the database
     *
     * @param username the name of the user
     * @param password the user's password
     * @return a User object if doesn't exist, null otherwise
     */
    public User createUser(String username, String password) throws PersistenceServiceException;

    /**
     * Updates the specified user's information. Proper usage of this
     * method requires call getUser first to obtain a valid User
     * instance.
     *
     * @param user a User object instance
     * @return true if updated, false otherwise
     */
    public boolean updateUser(User user) throws PersistenceServiceException;

    /**
     * Gets the specified user's information
     *
     * @param username the name of the user
     * @return a User object if exists, null otherwise
     */
    public User    getUser(String username);

    /**
     * Deletes the specified user's information
     *
     * @param username the name of the user
     * @return true if successful, false otherwise
     */
    public boolean deleteUser(String username) throws PersistenceServiceException;

    /**
     * Checks if the specified user is logged in
     *
     * @param username the name of the user
     * @return true if logged in, false otherwise
     */
    public boolean isLoggedIn(String username);

    /**
     * Sets the login status of the specified user
     *
     * @param username the name of the user
     * @param status the login status to set (true or false)
     * @return true if successful, false otherwise
     */
    public boolean setLoginStatus(String username, boolean status);

    /**
     * Returns a list of all logged in users
     *
     * @return list of logged in users
     */
    public List<String> getLoggedInUsers();


    /**
     * Retrieves the user information associated with the specified user
     *
     * @param username the name of the user
     * @return a UserInfo object or null
     */
    public UserInfo getUserInfo(String username);
}