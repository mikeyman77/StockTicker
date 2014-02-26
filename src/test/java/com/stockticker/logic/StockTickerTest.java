package com.stockticker.logic;

import com.stockticker.StockQuote;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertEquals;

public class StockTickerTest {
    
    private final StockTickerService sts = StockTicker.INSTANCE;
    
    /**
     * Test of getStockQuotes method using one valid symbol.
    */
    @Test
    public void testGetStockQuotes() throws Exception {
        List<String> symbols = new ArrayList<>();
        List<StockQuote> stockQuotes = new ArrayList<>();
        
        symbols.add("YHOO");
        
        stockQuotes = sts.getStockQuotes(symbols);
        
        int expected = 1;
        int result = stockQuotes.size();
        
        assertEquals(result, expected);
    }
    
}
