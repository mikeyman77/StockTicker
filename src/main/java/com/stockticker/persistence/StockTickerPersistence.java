package com.stockticker.persistence;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import com.stockticker.User;
import com.stockticker.UserInfo;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

/**
 * <p>Provides services to query and update User and Stock data in the
 * database</p>
 *
 * @author Stuart Connall
 * @see PersistenceService
 * @see PersistenceServiceException
 * @version 1.0 3/01/2014
 */
public enum StockTickerPersistence implements PersistenceService {
    INSTANCE;

    static Logger logger;

    private TrackedStocksDAO trackedDAO = null;
    private UserDAO userDAO = null;

    /**
     * Performs necessary initialization.
     *
     * @throws PersistenceServiceException when either the UserDAO or TrackedStockDAO
     *         instances fail to initialize most likely due to database related issues.
     */
    public void start() throws PersistenceServiceException {
        //configure log4j
        logger = LogManager.getLogger(StockTickerPersistence.class.getName());
        PropertyConfigurator.configure("./config/log4j.properties");

        userDAO = new UserDAOImpl();
        trackedDAO = new TrackedStocksDAOImpl();

        logger.info("The Stock Ticker Persistence Service is ready for service.");
    }

    /**
     * Returns a List of stock symbols being tracked by a specific user
     *
     * @param username name of user
     * @return list of stock symbols
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    @Override
    public List<String> getTrackedStocks(String username) throws PersistenceServiceException {
        int userId = userDAO.getUserId(username);
        return trackedDAO.get(userId);
    }

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
    @Override
    public boolean trackStock(String username, String stock, boolean track) throws PersistenceServiceException {
        boolean stockTracked = true;

        int userId = userDAO.getUserId(username);
        int stockId = trackedDAO.getStockId(stock);

        if (track) {
            if (!trackedDAO.exists(userId, stockId)) {
                trackedDAO.add(userId, stockId);
            }
        } else {
            if (trackedDAO.exists(userId, stockId)) {
                trackedDAO.delete(userId, stockId);
            }
            else {
                stockTracked = false;
            }
        }

        return stockTracked;
    }

    /**
     * Tests if a stock is being tracked for a specific user
     *
     * @param username name of user
     * @param stock    stock symbol to check
     * @return         true if tracked, false otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    @Override
    public boolean isStockTracked(String username, String stock) throws PersistenceServiceException {
        int userId = userDAO.getUserId(username);
        int stockId = trackedDAO.getStockId(stock);
        return trackedDAO.exists(userId, stockId);
    }

    /**
     * Checks if a user exists in the database
     *
     * @param username the name of the user
     * @return true if exists, false otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    @Override
    public boolean userExists(String username) throws PersistenceServiceException {
        return userDAO.exists(username);
    }

    /**
     * Creates a new user in the database.
     *
     * @param username the name of the user
     * @param password the user's password
     * @return a User object if doesn't exist, null otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    @Override
    public User createUser(String username, String password) throws PersistenceServiceException {
        return userDAO.create(username, password);
    }

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
    @Override
    public boolean updateUser(User user) throws PersistenceServiceException {
        return userDAO.update(user);
    }

    /**
     * Gets the specified user's information
     *
     * @param username the name of the user
     * @return a User object if exists, null otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    @Override
    public User getUser(String username) throws PersistenceServiceException {
        return userDAO.get(username);
    }

    /**
     * Deletes the specified user's information
     *
     * @param username the name of the user
     * @return true if successful, false otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    @Override
    public boolean deleteUser(String username) throws PersistenceServiceException {
        return userDAO.delete(username);
    }

    /**
     * Checks if the specified user is logged in
     *
     * @param username the name of the user
     * @return true if logged in, false otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    @Override
    public boolean isLoggedIn(String username) throws PersistenceServiceException {
        return userDAO.isLoggedIn(username);
    }

    /**
     * Sets the login status of the specified user
     *
     * @param username the name of the user
     * @param status the login status to set (true or false)
     * @return true if successful, false otherwise
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    @Override
    public boolean setLoginStatus(String username, boolean status) throws PersistenceServiceException {
        return userDAO.setLoginStatus(username, status);
    }

    /**
     * Returns a list of all logged in users
     *
     * @return list of logged in users or an empty List
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    @Override
    public List<String> getLoggedInUsers() throws PersistenceServiceException {
        return userDAO.getLoggedInUsers();
    }

    /**
     * Retrieves the user information associated with the specified user
     *
     * @param username the name of the user
     * @return a UserInfo object or null
     * @throws PersistenceServiceException is thrown when a database related error
     *         occurs in the Persistence Service component.
     */
    @Override
    public UserInfo getUserInfo(String username) throws PersistenceServiceException {
        User user = getUser(username);
        return user == null ? null : user.getUserInfo();
    }

}