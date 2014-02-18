package com.stockticker.persistence;

import java.util.List;
import com.stockticker.Stock;
import com.stockticker.User;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
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
    private Stock google;
    private Stock apple;
    private Stock microsoft;

    /**
     * Sets up each test before they run
     */
    @Before
    public void setUp() {
        persistence = StockTickerPersistence.INSTANCE;
        persistence.createUser(PEDROIA, PASSWORD);
        persistence.updateUser(new User(PEDROIA, PASSWORD));
        persistence.createUser(ORTIZ, PASSWORD);
        persistence.updateUser(new User(ORTIZ, PASSWORD));
        persistence.createUser(VICTORINO, PASSWORD);
        persistence.updateUser(new User(VICTORINO, PASSWORD));
        google = new Stock(GOOG);
        apple = new Stock(AAPL);
        microsoft = new Stock(MSFT);
        persistence.trackStock(ORTIZ, google, true);
        persistence.trackStock(ORTIZ, apple, true);
        persistence.trackStock(ORTIZ, microsoft, true);
    }

    /**
     * Tests the getTrackedStocks method for a value.
     */
    @Test
    public void testGetTrackedStocks() {
        List<Stock> stocks = persistence.getTrackedStocks(ORTIZ);
        assertTrue("tracked stocks", (stocks.size() > 0));
    }

    /**
     * Tests the trackStock method
     */
    @Test
    public void testTrackStock() {
        persistence.trackStock(ORTIZ, google, true);
        assertTrue("stock tracked", persistence.isStockTracked(ORTIZ, google.getSymbol()));
    }

    /**
     * Tests the isStockTracked method is true
     */
    @Test
    public void testIsStockTrackedTrue() {
        persistence.trackStock(ORTIZ, google, true);
        assertTrue("stock tracked", persistence.isStockTracked(ORTIZ, google.getSymbol()));
    }

    /**
     * Tests the isStockTracked method is false
     */
    @Test
    public void testIsStockTrackedFalse() {
        assertFalse("stock tracked", persistence.isStockTracked(ORTIZ, "JAVA"));
    }

    /**
     * Tests the userExists method is true
     */
    @Test
    public void testUserExistsTrue() {
        assertTrue("user exists", persistence.userExists(ORTIZ));
    }

    /**
     * Tests the userExists method is false
     */
    @Test
    public void testUserExistsFalse() {
        assertFalse("user doesn't exist", persistence.userExists(CONNALL));
    }

    /**
     * Tests the createUser method
     */
    @Test
    public void testCreateUser() {
        User user = persistence.createUser(SCHILLING, PASSWORD);
        assertNotNull("create user", user);
    }

    /**
     * Tests the updateUser method
     */
    @Test
    public void testUpdateUser() {
        User ortiz = new User(ORTIZ, PASSWORD+"2014");
        persistence.updateUser(ortiz);
        assertEquals("update user", PASSWORD+"2014", persistence.loadUser(ORTIZ).getPassword());
    }

    /**
     * Tests the loadUser method
     */
    @Test
    public void testLoadUser() {
        assertNotNull("load user", persistence.loadUser(VICTORINO));
    }

    /**
     * Tests the deleteUser method
     */
    @Test
    public void testDeleteUser() {
        User manning = new User(MANNING, PASSWORD);
        persistence.updateUser(manning);
        assertTrue("delete user", persistence.deleteUser(manning.getUserName()));
    }

}