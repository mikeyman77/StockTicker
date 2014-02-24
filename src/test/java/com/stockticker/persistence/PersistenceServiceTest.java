package com.stockticker.persistence;

import java.util.List;
import com.stockticker.Stock;
import com.stockticker.User;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertNotNull;

/**
 * PersistenceService junit test case.
 *
 * @author  Stuart Connall
 * @version 1.0 02/12/2014
 */

public class PersistenceServiceTest {

    private PersistenceService persistence;
    private static final String PEDROIA = "Pedroia";
    private static final String ORTIZ = "Ortiz";
    private static final String VICTORINO = "Victorino";
    private static final String BRADY = "Brady";
    private static final String MANNING = "Manning";
    private static final String SCHILLING = "Schilling";
    private static final String CONNALL = "Connall";
    private static final String VEDDER = "Vedder";
    private static final String PASSWORD = "redsox";
    private static final String GOOG = "GOOG";
    private static final String AAPL = "AAPL";
    private static final String MSFT = "MSFT";
    private static final String ORCL = "ORCL";
    private User pedroia = new User(PEDROIA, PASSWORD);
    private User ortiz = new User(ORTIZ, PASSWORD);
    private User victorino = new User(VICTORINO, PASSWORD);
    private User vedder = new User(VEDDER, PASSWORD);
    private Stock google;
    private Stock apple;
    private Stock microsoft;
    private Stock oracle;

    /**
     * Sets up each test before they run
     */
    @Before
    public void setUp() {
        persistence = StockTickerPersistence.INSTANCE;
        persistence.createUser(pedroia);
        persistence.createUser(ortiz);
        persistence.createUser(victorino);
        google = new Stock(GOOG);
        apple = new Stock(AAPL);
        microsoft = new Stock(MSFT);
        persistence.trackStock(ortiz, google, true);
        persistence.trackStock(ortiz, apple, true);
        persistence.trackStock(ortiz, microsoft, true);
    }

    /**
     * Tests the getTrackedStocks method for a value.
     */
    @Test
    public void testGetTrackedStocks() {
        List<Stock> stocks = persistence.getTrackedStocks(ortiz);
        assertTrue("tracked stocks", (stocks.size() > 0));
    }

    /**
     * Tests the trackStock method returns true
     */
    @Test
    public void testTrackStockTrue() {
        assertTrue("track stock true", persistence.trackStock(ortiz, google, true));
    }

    /**
     * Tests the trackStock method returns false
     */
    @Test
    public void testTrackStockFalse() {
        persistence.trackStock(ortiz, google, false);
        assertFalse("track stock false", persistence.trackStock(ortiz, null, true));
    }
    /**
     * Tests the isStockTracked method is true
     */
    @Test
    public void testIsStockTrackedTrue() {
        assertTrue("is stock tracked true", persistence.isStockTracked(ortiz, google));
    }

    /**
     * Tests the isStockTracked method is false
     */
    @Test
    public void testIsStockTrackedFalse() {
        assertFalse("is stock tracked false", persistence.isStockTracked(ortiz, oracle));
    }

    /**
     * Tests the userExists method is true
     */
    @Test
    public void testUserExistsTrue() {
        assertTrue("user exists", persistence.userExists(ortiz.getUserName()));
    }

    /**
     * Tests the userExists method is false
     */
    @Test
    public void testUserExistsFalse() {
        User connall = new User(CONNALL, PASSWORD);
        assertFalse("user doesn't exist", persistence.userExists(connall.getUserName()));
    }

    /**
     * Tests the createUser method
     */
    @Test
    public void testCreateUser() {
        User schilling = new User(SCHILLING, PASSWORD);
        User user = persistence.createUser(schilling);
        assertNotNull("create user", user);
    }

    /**
     * Tests the createUser method returns null when User exists
     */
    @Test
    public void testCreateUserReturnsNullIfExist() {
        User user = persistence.createUser(ortiz);
        assertNull("create user when exists", user);
    }

    @Test
    public void testUpdateUserUpdatesPassword() {
        User ortiz = new User(ORTIZ, PASSWORD+"2014");
        persistence.updateUser(ortiz);
        assertEquals("update user updates password", PASSWORD + "2014", persistence.getUser(ortiz.getUserName()).getPassword());
    }

    @Test
    public void testUpdateUserTrue() {
        assertTrue("update user true", persistence.updateUser(ortiz));
    }

    @Test
    public void testUpdateUserFalse() {
        assertFalse("update user false", persistence.updateUser(vedder));
    }
      
    /**
     * Tests the getUser method for non null return
     */
    @Test
    public void testLoadUserNotNull() {
        assertNotNull("load user not null", persistence.getUser(victorino.getUserName()));
    }

    /**
     * Tests the getUser method for null return
     */
    @Test
    public void testLoadUserNull() {
        assertNull("load user null", persistence.getUser(vedder.getUserName()));
    }

    /**
     * Tests the deleteUser method for return true
     */
    @Test
    public void testDeleteUserTrue() {
        assertTrue("delete user true", persistence.deleteUser(ortiz));
    }

    /**
     * Tests the deleteUser method for return false
     */
    @Test
    public void testDeleteUserFalse() {
        assertFalse("delete user false", persistence.deleteUser(vedder));
    }

    /**
     * Tests the setLoginStatus method for TRUE
     */
    @Test
    public void testSetLoginStatusTrue() {
        persistence.setLoginStatus(ortiz.getUserName(), true);
        assertTrue("login status true", persistence.isLoggedIn(ortiz.getUserName()));
    }

    /**
     * Tests the setLoginStatus method for FALSE
     */
    @Test
    public void testSetLoginStatusUserNotFound() {
        persistence.setLoginStatus(vedder.getUserName(), true);
        assertFalse("login status false", persistence.isLoggedIn(ortiz.getUserName()));
    }

    /**
     * Tests the isLoggedIn method for TRUE
     */
    @Test
    public void testIsLoggedInTrue() {
        persistence.setLoginStatus(ortiz.getUserName(), true);
        assertTrue("is logged in true", persistence.isLoggedIn(ortiz.getUserName()));
    }

    /**
     * Tests the isLoggedIn method for FALSE
     */
    @Test
    public void testIsLoggedInFalse() {
        persistence.setLoginStatus(ortiz.getUserName(), false);
        assertFalse("is logged in false", persistence.isLoggedIn(ortiz.getUserName()));
    }

    /**
     * Tests the getLoggedInUsers method for non-null
     */
    @Test
    public void testIsLoggedInNotNull() {
        assertNotNull("users logged in true", persistence.getLoggedInUsers());
    }

    /**
     * Tests the getLoggedInUsers method for empty list
     */
    @Test
    public void testIsLoggedInReturnsEmptyList() {
        List<String> users = persistence.getLoggedInUsers();
        for (String user : users) {
            persistence.setLoginStatus(user, false);
        }
        assertTrue("no users logged in", persistence.getLoggedInUsers().isEmpty());
    }

}