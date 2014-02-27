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

    private Map<String,Stock> stocksMap = new TreeMap<String,Stock>();
    private Map<String,TrackedStocks> trackedStocksMap = new TreeMap<String,TrackedStocks>();
    private Map<String,User> usersMap = new TreeMap<String, User>();
    private PersistenceConnection persistence;
    private int userId = 0;

    /**
     * Default no-args constructor that gets an instance of the
     * PersistenceConnection interface.
     */
    private StockTickerPersistence() {
        persistence = PersistenceConnection.INSTANCE;
    }

    /**
     * Returns a List of stock symbols being tracked by a specific user
     *
     * @param username name of user
     * @return list of stock symbols
     */
    @Override
    public List<String> getTrackedStocks(String username) {
        TrackedStocks tracked = trackedStocksMap.get(username);
        return new ArrayList<String>(tracked.getStocks());
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
            if (username.equals("") || stock.equals(""))
                return false;

            TrackedStocks tracked = trackedStocksMap.get(username);
            if (track) {
                if (tracked != null) {
                    tracked.put(stock);
                }
                else {
                    tracked = new TrackedStocks(username);
                    trackedStocksMap.put(username, tracked);
                }
            }
            else {
                tracked.remove(stock);
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
        if (username.equals("") || stock.equals(""))
            return false;

        TrackedStocks tracked = trackedStocksMap.get(username);
        return tracked.isStockTracked(stock);
    }

    /**
     * Checks if a user exists in the database
     *
     * @param username the name of the user
     * @return true if exists, false otherwise
     */
    @Override
    public boolean userExists(String username) {

        UserDAO userDAO = new UserDAOImpl(persistence.getConnection());
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

        UserDAO userDAO = new UserDAOImpl(persistence.getConnection());
        User user = userDAO.create(username, password);

        return user;
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

        UserDAO userDAO = new UserDAOImpl(persistence.getConnection());
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

        UserDAO userDAO = new UserDAOImpl(persistence.getConnection());
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

        UserDAO userDAO = new UserDAOImpl(persistence.getConnection());
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

        UserDAO userDAO = new UserDAOImpl(persistence.getConnection());
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

        UserDAO userDAO = new UserDAOImpl(persistence.getConnection());
        return userDAO.setLoginStatus(username, status);
    }

    /**
     * Returns a list of all logged in users
     *
     * @return list of logged in users or an empty List
     */
    @Override
    public List<String> getLoggedInUsers() {

        UserDAO userDAO = new UserDAOImpl(persistence.getConnection());
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


    public static void main(String [] args) {
        PersistenceService ps = StockTickerPersistence.INSTANCE;

        User sconnall = ps.createUser("connall", "password");

        //Set logged in and test
        ps.setLoginStatus(sconnall.getUserName(), true);
        System.out.print("User " + sconnall.getUserName() + " is ");
        if (ps.isLoggedIn(sconnall.getUserName())) {
            System.out.println("logged in.");
        }
        else {
            System.out.println("not logged out.");
        }
        sconnall = ps.getUser(sconnall.getUserName());
        sconnall.setPassword("PATRIOTS");
        ps.updateUser(sconnall);
        sconnall.setPassword("BRUINS");
        ps.updateUser(sconnall);
        //ps.deleteUser(sconnall.getUserName());
        //ps.deleteUser(sconnall.getUserName());

        //set logged out and test
        ps.setLoginStatus(sconnall.getUserName(), false);
        System.out.print("User " + sconnall.getUserName() + " is ");
        if (ps.isLoggedIn(sconnall.getUserName())) {
            System.out.println("logged in.");
        }
        else {
            System.out.println("logged out.");
        }

        //Set logged in and test
        ps.setLoginStatus(sconnall.getUserName(), true);
        System.out.print("User " + sconnall.getUserName() + " is ");
        if (ps.isLoggedIn(sconnall.getUserName())) {
            System.out.println("logged in.");
        }
        else {
            System.out.println("logged out.");
        }

        //Create new user and log in
        User sconnall2 = ps.createUser("connall2", "password");
        ps.setLoginStatus(sconnall2.getUserName(), true);
        System.out.print("User " + sconnall2.getUserName() + " is ");
        if (ps.isLoggedIn(sconnall2.getUserName())) {
            System.out.println("logged in.");
        }
        else {
            System.out.println("logged out.");
        }

        List<String> loggedInUsers = ps.getLoggedInUsers();
        for (String username : loggedInUsers) {
            System.out.print("User " + username + " is logged in");
        }
    }

}