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
 * Provides services to query and update User and Stock data from the
 * database
 *
 * @author Stuart Connall
 * @version 1.0 3/01/2014
 */
public enum StockTickerPersistence implements PersistenceService {
    INSTANCE;

    static Logger logger;

    private TrackedStocksDAO trackedDAO = null;
    private UserDAO userDAO = null;

    /*
     * This is temporary code that invokes the PersistenceService start method
     *       on behalf of junit tests and the business logic. This private contructor
     *       can be removed once the start method is invoked after retrieving the
     *       PersistenceService interface instance.
     */
    private StockTickerPersistence() {
        boolean isBusinessLogicOrJUnitTest = false;
        String BUSINESS_LOGIC_CLASS = "com.stockticker.logic.UserAuthorization";
        String PERSISTENCE_SERVICE_TEST = "com.stockticker.persistence.PersistenceServiceTest";
        String USER_AUTHORIZATION_TEST = "com.stockticker.logic.UserAuthorizationTest";
        String STOCK_TICKER_TEST = "com.stockticker.logic.StockTickerTest";
        try {
            List<StackTraceElement> elements = new ArrayList<StackTraceElement>(Arrays.asList(Thread.currentThread().getStackTrace()));
            for (StackTraceElement element : elements) {
                if (element.getClassName().equals(BUSINESS_LOGIC_CLASS) ||
                    element.getClassName().equals(PERSISTENCE_SERVICE_TEST) ||
                    element.getClassName().equals(USER_AUTHORIZATION_TEST) ||
                    element.getClassName().equals(STOCK_TICKER_TEST)) {

                    isBusinessLogicOrJUnitTest = true;
                    break;
                }
            }
            if (isBusinessLogicOrJUnitTest) {
                start();
            }
        }
        catch (PersistenceServiceException e) {
            ; //do nothing, supress. see todo above
        }
    }

    /**
     * Performs necessary initialization.
     *
     * @throws PersistenceServiceException
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
     * @throws PersistenceServiceException
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
     * @throws PersistenceServiceException
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
     * @throws PersistenceServiceException
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
     * @throws PersistenceServiceException
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
     * @throws PersistenceServiceException
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
     * @throws PersistenceServiceException
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
     * @throws PersistenceServiceException
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
     * @throws PersistenceServiceException
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
     * @throws PersistenceServiceException
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
     * @throws PersistenceServiceException
     */
    @Override
    public boolean setLoginStatus(String username, boolean status) throws PersistenceServiceException {
        return userDAO.setLoginStatus(username, status);
    }

    /**
     * Returns a list of all logged in users
     *
     * @return list of logged in users or an empty List
     * @throws PersistenceServiceException
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
     * @throws PersistenceServiceException
     */
    @Override
    public UserInfo getUserInfo(String username) throws PersistenceServiceException {
        User user = getUser(username);
        return user == null ? null : user.getUserInfo();
    }


    /*
     * This is for testing purposes only in order to run in the debugger.
     */

/*
    public static void main(String [] args) throws PersistenceServiceException {
        PersistenceService ps = StockTickerPersistence.INSTANCE;

        //User test calls go here
        User sconnall = ps.createUser("connall", "password");
        ps.setLoginStatus(sconnall.getUserName(), true);

        //Stock test calls go here
        User sconnall2 = ps.createUser("connall2", "password");
        ps.setLoginStatus(sconnall2.getUserName(), true);
        ps.trackStock(sconnall2.getUserName(), "GOOG", true);
        ps.trackStock(sconnall2.getUserName(), "AAPL", true);
        ps.trackStock(sconnall2.getUserName(), "MSFT", true);
        ps.isStockTracked(sconnall2.getUserName(), "GOOG");
        ps.trackStock(sconnall2.getUserName(), "MSFT", false);
        ps.isStockTracked(sconnall2.getUserName(), "MSFT");
        List<String> tracked = ps.getTrackedStocks(sconnall2.getUserName());
        System.out.println("The following stocks are currently being tracked :");
        int i = 0;
        int numTracked = tracked.size();
        for (String stock : tracked) {
            i++;
            System.out.println(stock);
            if (i < numTracked-1) {
                System.out.print(",");
            }
        }
        System.out.println("");
        System.exit(0);
    }
*/
}