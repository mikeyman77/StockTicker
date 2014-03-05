package com.stockticker.logic;

import com.stockticker.User;
import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.StockTickerPersistence;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

public class StockTickerTest {
    
    private final StockTickerService sts = StockTicker.INSTANCE;
    private final PersistenceService persist = StockTickerPersistence.INSTANCE;
    
    private final User testUser = new User("testuser", "testpassword");
    private final String stockSymbol = "AAPL";

    @Before
    public void setUp() throws Exception {
        persist.createUser(testUser.getUserName(), testUser.getPassword());
    }

    @After
    public void tearDown() throws Exception {
        persist.deleteUser(testUser.getUserName());
    }
    
    /**
     * Test of getStockQuotes method.
    */
    @Test
    public void testGetStockQuotes() throws Exception {
        
    }

    /**
     * Test of getTrackedStocks method with one tracked stock.
     */
    @Test
    public void testGetTrackedStocks() {
        persist.trackStock(testUser.getUserName(), stockSymbol, true);
        String username = testUser.getUserName();
        boolean result = sts.getTrackedStocks(username).contains(stockSymbol);
        assertTrue(result);
    }

    /**
     * Test of trackStock method to track a stock for a registered user.
     */
    @Test
    public void testTrackStock() {
        String username = testUser.getUserName();
        String symbol = stockSymbol;
        boolean result = sts.trackStock(username, symbol, true);
        assertTrue(result);
    }
    
    /**
     * Test of trackStock method to un-track a stock for a registered user.
     */
    @Test
    public void testUnTrackStock() {
        String username = testUser.getUserName();
        String symbol = stockSymbol;
        boolean result = sts.trackStock(username, symbol, false);
        assertFalse(result);
    }
    
    /**
     * Test of isStockTracked method when a user is not tracking a specific stock.
     */
    @Test
    public void testIsStockTrackedFalse() {
        String username = testUser.getUserName();
        String symbol = stockSymbol;
        boolean result = sts.isStockTracked(username, symbol);
        assertFalse(result);
    }
    
    /**
     * Test of isStockTracked method when a user is tracking a specified stock.
     */
    @Test
    public void testIsStockTrackedTrue() {
        String username = testUser.getUserName();
        String symbol = "AAPL";
        sts.trackStock(username, symbol, true);
        boolean result = sts.isStockTracked(username, symbol);
        assertTrue(result);
    }
    
}
