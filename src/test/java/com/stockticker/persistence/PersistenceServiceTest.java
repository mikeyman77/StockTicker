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
        pedroia = new User("Pedroia");
        ortiz   = new User("Ortiz");
        victorino = new User("Victorino");
        persistence.saveUser(pedroia);
        persistence.saveUser(ortiz);
        persistence.saveUser(victorino);
        google = new Stock("GOOG");
        apple = new Stock("AAPPL");
        microsoft = new Stock("MSFT");
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
        assertFalse("user doesn't exist", persistence.userExists(new User("Connall")));
    }

    /**
     * Tests the saveUser method
     */
    @Test
    public void testSaveUser() {
        User brady = new User("Brady");
        persistence.saveUser(brady);
        assertTrue("save user", persistence.userExists(brady));
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
        User manning = new User("Manning");
        persistence.saveUser(manning);
        assertTrue("delete user", persistence.deleteUser(manning));
    }

}