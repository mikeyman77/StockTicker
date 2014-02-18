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
    private User pedroia;
    private User ortiz;
    private User victorino;
    private Stock google;
    private Stock apple;
    private Stock microsoft;

    /**
     * Sets up each test before they run
     */
    @Before
    public void setUp() {
        persistence = StockTickerPersistence.INSTANCE;
        pedroia = new User("Pedroia", "redsox");
        ortiz   = new User("Ortiz", "redsox");
        victorino = new User("Victorino", "redsox");
        persistence.updateUser(pedroia);
        persistence.updateUser(ortiz);
        persistence.updateUser(victorino);
        google = new Stock("GOOG");
        apple = new Stock("AAPPL");
        microsoft = new Stock("MSFT");
        persistence.trackStock(ortiz.getUserName(), google, true);
        persistence.trackStock(ortiz.getUserName(), apple, true);
        persistence.trackStock(ortiz.getUserName(), microsoft, true);
    }

    /**
     * Tests the getTrackedStocks method for a value.
     */
    @Test
    public void testGetTrackedStocks() {
        List<Stock> stocks = persistence.getTrackedStocks(ortiz.getUserName());
        assertTrue("tracked stocks", (stocks.size() > 0));
    }

    /**
     * Tests the trackStock method
     */
    @Test
    public void testTrackStock() {
        persistence.trackStock(ortiz.getUserName(), google, true);
        assertTrue("stock tracked", persistence.isStockTracked(ortiz.getUserName(), google.getSymbol()));
    }

    /**
     * Tests the isStockTracked method is true
     */
    @Test
    public void testIsStockTrackedTrue() {
        persistence.trackStock(ortiz.getUserName(), google, true);
        assertTrue("stock tracked", persistence.isStockTracked(ortiz.getUserName(), google.getSymbol()));
    }

    /**
     * Tests the isStockTracked method is false
     */
    @Test
    public void testIsStockTrackedFalse() {
        assertFalse("stock tracked", persistence.isStockTracked(ortiz.getUserName(), "JAVA"));
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
        assertFalse("user doesn't exist", persistence.userExists("Connall"));
    }

    /**
     * Tests the saveUser method
     */
    @Test
    public void testSaveUser() {
        User brady = new User("Brady", "redsox");
        persistence.updateUser(brady);
        assertTrue("update user", persistence.userExists(brady.getUserName()));
    }

    /**
     * Tests the loadUser method
     */
    @Test
    public void testLoadUser() {
        assertNotNull("load user", persistence.loadUser(victorino.getUserName()));
    }

    /**
     * Tests the deleteUser method
     */
    @Test
    public void testDeleteUser() {
        User manning = new User("Manning", "redsox");
        persistence.updateUser(manning);
        assertTrue("delete user", persistence.deleteUser(manning.getUserName()));
    }

}