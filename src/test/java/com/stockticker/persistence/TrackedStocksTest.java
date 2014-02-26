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
    private static final String GOOG = "GOOG";
    private static final String AAPL = "AAPL";
    private static final String MSFT = "MSFT";
    private static final String ORCL = "ORCL";

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
        assertFalse("put stock false", tracked.put(""));
    }

    /**
     * Tests the TrackedStocks remove for removal of Stock
     */
    @Test
    public void testRemoveTrue() {
        assertTrue("remove Stock true", tracked.remove(GOOG));
    }

    /**
     * Tests the TrackedStocks for failure to remove stock, most
     * likely because it doesn't exist
     */
    @Test
    public void testRemoveNull() {
        assertFalse("remove Stock false", tracked.remove(ORCL));
    }
}
