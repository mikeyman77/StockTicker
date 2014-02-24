package com.stockticker.persistence;

import com.stockticker.Stock;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertNotNull;

/**
 * Tests the TrackedStocks class
 *
 * @author Stuart Connall
 */
public class TrackedStocksTest {

    private TrackedStocks tracked;

    private static final String USER1 = "sconnall";
    private static final Stock GOOG = new Stock("GOOG");
    private static final Stock AAPL = new Stock("AAPL");
    private static final Stock MSFT = new Stock("MSFT");
    private static final Stock ORCL = new Stock("ORCL");

    /**
     * Sets up the environment by instantiating the
     * TrackedStock class and adding two Stocks.
     */
    @Before
    public void setUp() {
        tracked = new TrackedStocks(USER1);
        tracked.put(GOOG);
        tracked.put(AAPL);
    }

    /**
     * Tests the TrackedStocks put for true
     */
    @Test
    public void testPutTrue() {
        assertTrue("put stock true", tracked.put(MSFT));
    }

    /**
     * Tests the TrackedStocks put for false
     */
    @Test
    public void testPutFalse() {
        assertFalse("put stock false", tracked.put(null));
    }

    /**
     * Tests the TrackedStocks remove for removal of Stock
     */
    @Test
    public void testRemoveNotNull() {
        assertNotNull("remove Stock not null", tracked.remove(GOOG));
    }

    /**
     * Tests the TrackedStocks for failure to remove stock, most
     * likely because it doesn't exist or is null
     */
    @Test
    public void testRemoveNull() {
        assertNull("remove Stock null", tracked.remove(ORCL));
    }
}
