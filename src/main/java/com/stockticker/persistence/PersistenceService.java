package com.stockticker.persistence;

import java.util.List;
import com.stockticker.User;
import com.stockticker.UserInfo;

/**
 * <p>Provides public interface methods for the Persistence Service component.</p>
 *
 * @author Stuart Connall
 * @see StockTickerPersistence
 * @see PersistenceServiceException
 * @version 1.0 03/11/2014
 */
public interface PersistenceService {

    /**
     * Starts the Persistence Service which performs necessary initialization.
     *
     * @throws PersistenceServiceException when either the UserDAO or TrackedStockDAO
     *         instances fail to initialize most likely due to database related issues.
     */
    public void start() throws PersistenceServiceException;


    /**
     * Returns a List of stock symbols being tracked by a specific user
     *
     * @param username name of user
     * @return list of stock symbols
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    public List<String> getTrackedStocks(String username) throws PersistenceServiceException;

    /**
     * Sets whether or not a stock is to be tracked
     *
     * @param username name of user
     * @param stock    stock symbol to track
     * @param track    true to track, false to un-track
     * @return true or false
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    public boolean trackStock(String username, String stock, boolean track) throws PersistenceServiceException;

    /**
     * Tests if a stock is being tracked for a specific user
     *
     * @param username name of user
     * @param stock    stock symbol to check
     * @return         true if tracked, false otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    public boolean isStockTracked(String username, String stock) throws PersistenceServiceException;

    /**
     * Checks if a user exists in the database
     *
     * @param username the name of the user
     * @return true if exists, false otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    public boolean userExists(String username) throws PersistenceServiceException;

    /**
     * Creates a new user in the database
     *
     * @param username the name of the user
     * @param password the user's password
     * @return a User object if doesn't exist, null otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    public User createUser(String username, String password) throws PersistenceServiceException;

    /**
     * Updates the specified user's information. Proper usage of this
     * method requires call getUser first to obtain a valid User
     * instance.
     *
     * @param user a User object instance
     * @return true if updated, false otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    public boolean updateUser(User user) throws PersistenceServiceException;

    /**
     * Gets the specified user's information
     *
     * @param username the name of the user
     * @return a User object if exists, null otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    public User    getUser(String username) throws PersistenceServiceException;

    /**
     * Deletes the specified user's information
     *
     * @param username the name of the user
     * @return true if successful, false otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    public boolean deleteUser(String username) throws PersistenceServiceException;

    /**
     * Checks if the specified user is logged in
     *
     * @param username the name of the user
     * @return true if logged in, false otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    public boolean isLoggedIn(String username) throws PersistenceServiceException;

    /**
     * Sets the login status of the specified user
     *
     * @param username the name of the user
     * @param status the login status to set (true or false)
     * @return true if successful, false otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    public boolean setLoginStatus(String username, boolean status) throws PersistenceServiceException;

    /**
     * Returns a list of all logged in users
     *
     * @return list of logged in users
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    public List<String> getLoggedInUsers() throws PersistenceServiceException;


    /**
     * Retrieves the user information associated with the specified user
     *
     * @param username the name of the user
     * @return a UserInfo object or null
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    public UserInfo getUserInfo(String username) throws PersistenceServiceException;
}