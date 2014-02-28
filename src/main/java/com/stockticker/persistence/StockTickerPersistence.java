package com.stockticker.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import com.stockticker.Stock;
import com.stockticker.User;
import com.stockticker.UserInfo;

/**
 * Provides services to query and update User and Stock data from the
 * database
 */
public enum StockTickerPersistence implements PersistenceService {
    INSTANCE;

    private PersistenceConnection persistence;
    private TrackedStocksDAO trackedDAO;
    private UserDAO userDAO;

    /**
     * Default no-args constructor that gets an instance of the
     * PersistenceConnection interface.
     */
    private StockTickerPersistence() {
        persistence = PersistenceConnection.INSTANCE;
        userDAO = new UserDAOImpl(persistence.getConnection());
        trackedDAO = new TrackedStocksDAOImpl(persistence.getConnection());
    }

    /**
     * Returns a List of stock symbols being tracked by a specific user
     *
     * @param username name of user
     * @return list of stock symbols
     */
    @Override
    public List<String> getTrackedStocks(String username) {
        int userId = userDAO.getUserId(username);
        return trackedDAO.get(userId);
    }

    /**
     * Sets whether or not a stock is to be tracked
     *
     * @param username name of user
     * @param stock    stock symbol to track
     * @param track    true to track, false to untrack
     * @return true or false
     */
    @Override
    public boolean trackStock(String username, String stock, boolean track) {

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
            else return false;
        }

        return true;
    }

    /**
     * Tests if a stock is being tracked for a specific user
     *
     * @param username name of user
     * @param stock    stock symbol to check
     * @return         true if tracked, false otherwise
     */
    @Override
    public boolean isStockTracked(String username, String stock) {

        int userId = userDAO.getUserId(username);
        int stockId = trackedDAO.getStockId(stock);

        return trackedDAO.exists(userId, stockId);
    }

    /**
     * Checks if a user exists in the database
     *
     * @param username the name of the user
     * @return true if exists, false otherwise
     */
    @Override
    public boolean userExists(String username) {

        return userDAO.exists(username);
    }

    /**
     * Creates a new user in the database
     *
     * @param username the name of the user
     * @param password the user's password
     * @return a User object if doesn't exist, null otherwise
     */
    @Override
    public User createUser(String username, String password) {

        return userDAO.create(username, password);
    }

    /**
     * Updates the specified user's information
     *
     * @param user a User object instance
     * @return true if updated, false otherwise
     */
    @Override
    public boolean updateUser(User user) {
        if (user == null)
            return false;

        return userDAO.update(user);
    }

    /**
     * Gets the specified user's information
     *
     * @param username the name of the user
     * @return a User object if exists, null otherwise
     */
    @Override
    public User getUser(String username) {

        return userDAO.get(username);
    }

    /**
     * Deletes the specified user's information
     *
     * @param username the name of the user
     * @return true if successful, false otherwise
     */
    @Override
    public boolean deleteUser(String username) {

        return userDAO.delete(username);
    }

    /**
     * Checks if the specified user is logged in
     *
     * @param username the name of the user
     * @return true if logged in, false otherwise
     */
    @Override
    public boolean isLoggedIn(String username) {

        return userDAO.isLoggedIn(username);
    }

    /**
     * Sets the login status of the specified user
     *
     * @param username the name of the user
     * @param status the login status to set (true or false)
     * @return true if successful, false otherwise
     */
    @Override
    public boolean setLoginStatus(String username, boolean status) {

        return userDAO.setLoginStatus(username, status);
    }

    /**
     * Returns a list of all logged in users
     *
     * @return list of logged in users or an empty List
     */
    @Override
    public List<String> getLoggedInUsers() {

        return userDAO.getLoggedInUsers();
    }

    /**
     * Retrieves the user information associated with the specified user
     *
     * @param username the name of the user
     * @return a UserInfo object or null
     */
    @Override
    public UserInfo getUserInfo(String username) {

        User user = getUser(username);
        return user == null ? null : user.getUserInfo();
    }


    /*
     * This is for testing purposes only, to run in the debugger.
     */
    public static void main(String [] args) {
        PersistenceService ps = StockTickerPersistence.INSTANCE;

        User sconnall2 = ps.createUser("connall2", "password");
        ps.setLoginStatus(sconnall2.getUserName(), true);
        ps.trackStock(sconnall2.getUserName(), "GOOG", true);
        ps.trackStock(sconnall2.getUserName(), "AAPL", true);
        ps.trackStock(sconnall2.getUserName(), "MSFT", true);
        ps.isStockTracked(sconnall2.getUserName(), "GOOG");
        ps.trackStock(sconnall2.getUserName(), "MSFT", false);
        ps.isStockTracked(sconnall2.getUserName(), "MSFT");
        List<String> tracked = ps.getTrackedStocks(sconnall2.getUserName());
        System.out.println("");
        System.exit(0);
    }

}