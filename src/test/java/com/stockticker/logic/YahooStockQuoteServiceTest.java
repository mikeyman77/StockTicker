
package com.stockticker.logic;

import com.stockticker.StockQuote;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests the Yahoo stock quote service.
 * 
 * @author Michael Grissom
 */
public class YahooStockQuoteServiceTest {
    
    private final YahooStockQuoteService instance = YahooStockQuoteService.INSTANCE;
    
    /**
     * Test of getURL method with one valid symbol.
     */
    @Test
    public void testGetURLWithOneValidSymbol() throws Exception {
        List<String> symbols = new ArrayList<>();
        symbols.add("GOOG");
        
        URL url = instance.getURL(symbols);
        String urlStr = url.toString();
        boolean result = urlStr.contains("\"GOOG\"");
        
        assertTrue(result);
    }
    
    /**
     * Test of getURL method with two valid symbols.
     */
    @Test
    public void testGetURLWithTwoValidSymbol() throws Exception {
        List<String> symbols = new ArrayList<>();
        symbols.add("GOOG");
        symbols.add("AAPL");
        
        URL url = instance.getURL(symbols);
        String urlStr = url.toString();
        boolean result = urlStr.contains("\"GOOG\",\"AAPL\"");
        
        assertTrue(result);
    }
    
    /**
     * Test of getURL method with an invalid symbol at the beginning of the 
     * list, and a valid symbol at the end.
     */
    @Test
    public void testGetURLWithOneInvalidSymbolAndOneValidSymbol() throws Exception {
        List<String> symbols = new ArrayList<>();
        symbols.add("XXXX");
        symbols.add("AAPL");
        
        URL url = instance.getURL(symbols);
        String urlStr = url.toString();
        boolean result = urlStr.contains("\"AAPL\"") 
                && !urlStr.contains("XXXX");
        
        assertTrue(result);
    }
    
    /**
     * Test of getURL method with a valid symbol at the beginning of the 
     * list, and an invalid symbol at the end.
     */
    @Test
    public void testGetURLWithOnevalidSymbolAndOneInvalidSymbol() throws Exception {
        List<String> symbols = new ArrayList<>();
        symbols.add("AAPL");
        symbols.add("XXXX");
        
        URL url = instance.getURL(symbols);
        String urlStr = url.toString();
        boolean result = urlStr.contains("\"AAPL\"") 
                && !urlStr.contains("XXXX");
        
        assertTrue(result);
    }
    
    /**
     * Test of getURL method with multiple valid and invalid symbols in the list.
     */
    @Test
    public void testGetURLWithMultipleInvalidSymbols() throws Exception {
        List<String> symbols = new ArrayList<>();
        symbols.add("AAPL");
        symbols.add("XXXX");
        symbols.add("GOOG");
        symbols.add("YYYY");
        
        URL url = instance.getURL(symbols);
        String urlStr = url.toString();
        boolean result = urlStr.contains("\"AAPL\",\"GOOG\"") 
                && !urlStr.contains("XXXX")
                && !urlStr.contains("YYYY");
        
        assertTrue(result);
    }
    
    /**
     * Test of getURL method with an empty symbols list.
     */
    @Test
    public void testGetURLwithEmptySymbolsList() throws Exception {
        List<String> symbols = new ArrayList<>();

        assertNull(instance.getURL(symbols));
    }
    
    /**
     * Test of getURL method with null.
     */
    @Test
    public void testGetURLWithNull() throws Exception {

        assertNull(instance.getURL(null));
    }
    
    /**
     * Test of getStockQuotes method with one symbol
     */
    @Test
    public void testGetStockQuotesWithNoSymbols() throws Exception {
        
        // use test json results file
        File file = new File("resources/stockquotedata0results.json");
        URL fileUrl = file.toURI().toURL();
        
        int stockQuoteCount = instance.getStockQuotes(fileUrl).size();
        int expResult = 0;
        
        assertEquals(expResult, stockQuoteCount);
    }
    
    /**
     * Test of getStockQuotes method with one symbol
     */
    @Test
    public void testGetStockQuotesWithOneSymbol() throws Exception {
        
        // use test json results file
        File file = new File("resources/stockquotedata1result.json");
        URL fileUrl = file.toURI().toURL();
        
        int stockQuoteCount = instance.getStockQuotes(fileUrl).size();
        int expResult = 1;
        
        assertEquals(expResult, stockQuoteCount);
    }
    
    /**
     * Test of getStockQuotes method with multiple symbols
     */
    @Test
    public void testGetStockQuotesWithMultipleSymbols() throws Exception {
        
        // use test json results file
        File file = new File("resources/stockquotedata2results.json");
        URL fileUrl = file.toURI().toURL();
        
        int stockQuoteCount = instance.getStockQuotes(fileUrl).size();
        int expResult = 2;
        
        assertEquals(expResult, stockQuoteCount);
    }
    
    /**
     * Test of getStockQuotes method with null symbol list.
     */
    @Test
    public void testGetStockQuotesWithNull() throws Exception {
        
        List<StockQuote> stockQuotes = instance.getStockQuotes(null);
        
        int stockQuoteCount = stockQuotes.size();
        int expResult = 0;
        
        assertEquals(expResult, stockQuoteCount);
    }
}
