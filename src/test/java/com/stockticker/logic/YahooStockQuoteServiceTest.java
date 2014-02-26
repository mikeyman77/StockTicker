
package com.stockticker.logic;

import com.stockticker.StockQuote;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 *
 * @author mikeyman77
 */
public class YahooStockQuoteServiceTest {
    
    private final YahooStockQuoteService instance = YahooStockQuoteService.INSTANCE;
    
    /**
     * Test of constructURL method with one valid symbol.
     */
    @Test
    public void testConstructURLwithOneSymbol() throws Exception {
        List<String> symbols = new ArrayList<>();
        symbols.add("GOOG");
        
        boolean result = instance.constructURL(symbols);
        
        assertTrue(result);
    }
    
    /**
     * Test of constructURL method with multiple valid symbols.
     */
    @Test
    public void testConstructURLwithMultipleSymbols() throws Exception {
        List<String> symbols = new ArrayList<>();
        symbols.add("GOOG");
        symbols.add("AAPL");
        symbols.add("YHOO");
        
        boolean result = instance.constructURL(symbols);
        
        assertTrue(result);
    }
    
    /**
     * Test of constructURL method with an empty symbols list.
     */
    @Test
    public void testConstructURLwithEmptySymbolsList() throws Exception {
        List<String> symbols = new ArrayList<>();
        
        boolean result = instance.constructURL(symbols);
        
        assertFalse(result);
    }
    
    /**
     * Test of getStocks method with multiple symbols
     */
    @Test
    public void testGetStocks() throws Exception {
        List<String> symbols = new ArrayList<>();
        symbols.add("GOOG");
        symbols.add("AAPL");
        instance.constructURL(symbols);
        
        List<StockQuote> stockQuotes = instance.getStocks();
        
        int stockQuoteCount = stockQuotes.size();
        int expResult = 2;
                
        assertEquals(expResult, stockQuoteCount);
    }
    
    /**
     * Test of getStocks method with no URL setup
     */
    @Test
    public void testGetStocksWithNoUrlSet() throws Exception {
        int expResult = 0;
        int result = instance.getStocks().size();
        assertEquals(expResult, result);
    }
    
}
