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
    private static final String PASSWORD = "redsox";
    private static final String GOOG = "GOOG";
    private static final String AAPL = "AAPL";
    private static final String MSFT = "MSFT";
    private User pedroia = new User(PEDROIA, PASSWORD);
    private User ortiz = new User(ORTIZ, PASSWORD);
    private User victorino = new User(VICTORINO, PASSWORD);
    private Stock google;
    private Stock apple;
    private Stock microsoft;

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
     * Tests the trackStock method
     */
    @Test
    public void testTrackStock() {
        persistence.trackStock(ortiz, google, true);
        assertTrue("stock tracked", persistence.isStockTracked(ortiz, google));
    }

    /**
     * Tests the isStockTracked method is true
     */
    @Test
    public void testIsStockTrackedTrue() {
        persistence.trackStock(ortiz, google, true);
        assertTrue("stock tracked", persistence.isStockTracked(ortiz, google));
    }

    /**
     * Tests the isStockTracked method is false
     */
    @Test
    public void testIsStockTrackedFalse() {
        assertFalse("stock tracked", persistence.isStockTracked(ortiz, new Stock("JAVA")));
    }

    /**
     * Tests the userExists method is true
     */
    @Test
    public void testUserExistsTrue() {
        assertTrue("user exists", persistence.userExists(ortiz));
    }

    /**
     * Tests the userExists method is false
     */
    @Test
    public void testUserExistsFalse() {
        User connall = new User(CONNALL, PASSWORD);
        assertFalse("user doesn't exist", persistence.userExists(connall));
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
    public void testUpdateUser() {
        User ortiz = new User(ORTIZ, PASSWORD+"2014");
        persistence.updateUser(ortiz);
        assertEquals("update user", PASSWORD+"2014", persistence.loadUser(ortiz).getPassword());
    }

    /**
     * Tests the loadUser method
     */
    @Test
    public void testLoadUser() {
        assertNotNull("load user", persistence.loadUser(victorino));
    }

    /**
     * Tests the deleteUser method
     */
    @Test
    public void testDeleteUser() {
        assertTrue("delete user", persistence.deleteUser(ortiz));
    }

    /**
     * Tests the setLoginStatus method for TRUE
     */
    @Test
    public void testSetLoginStatusTrue() {
        ortiz.setLoggedIn(true);
        persistence.setLoginStatus(ortiz);
        assertTrue("login status true", persistence.isLoggedIn(ortiz));
    }

    /**
     * Tests the setLoginStatus method for FALSE
     */
    @Test
    public void testSetLoginStatusFalse() {
        ortiz.setLoggedIn(false);
        persistence.setLoginStatus(ortiz);
        assertFalse("login status false", persistence.isLoggedIn(ortiz));
    }

    /**
     * Tests the isLoggedIn method for TRUE
     */
    @Test
    public void testIsLoggedInTrue() {
        ortiz.setLoggedIn(true);
        persistence.setLoginStatus(ortiz);
        assertTrue("is logged in true", persistence.isLoggedIn(ortiz));
    }

    /**
     * Tests the isLoggedIn method for FALSE
     */
    @Test
    public void testIsLoggedInFalse() {
        ortiz.setLoggedIn(false);
        persistence.setLoginStatus(ortiz);
        assertFalse("is logged in false", persistence.isLoggedIn(ortiz));
    }
}