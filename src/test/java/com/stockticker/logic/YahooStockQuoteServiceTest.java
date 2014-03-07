
package com.stockticker.logic;

import com.stockticker.StockQuote;
import java.io.File;
import java.io.InputStream;
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
     * test getInputStream method with 2 stock quote results returned.
     */
    @Test
    public void testGetInputStreamWith2Results() throws Exception {
        
        File file = new File("resources/stockquotedata2results.json");
        URL fileUrl = file.toURI().toURL();
        
        InputStream inputStream = instance.getInputStream(fileUrl);
        int bytesCount = inputStream.available();
        int expResult = 984;
        
        assertEquals(expResult, bytesCount);
    }
    
    /**
     * test getInputStream method with 1 stock quote result returned.
     */
    @Test
    public void testGetInputStreamWith1Result() throws Exception {
        
        File file = new File("resources/stockquotedata1result.json");
        URL fileUrl = file.toURI().toURL();
        
        InputStream inputStream = instance.getInputStream(fileUrl);
        int bytesCount = inputStream.available();
        int expResult = 538;
        
        assertEquals(expResult, bytesCount);
    }
    
    /**
     * test getInputStream method with 1 stock quote result returned.
     */
    @Test
    public void testGetInputStreamWithNoResults() throws Exception {
        
        File file = new File("resources/stockquotedata0results.json");
        URL fileUrl = file.toURI().toURL();
        
        InputStream inputStream = instance.getInputStream(fileUrl);
        int bytesCount = inputStream.available();
        int expResult = 84;
        
        assertEquals(expResult, bytesCount);
    }
    
    /**
     * test getInputStream method when URL is null.
     */
    @Test
    public void testGetInputStreamWithNullURL() throws Exception {
        
        InputStream inputStream = instance.getInputStream(null);
        assertNull(inputStream);
    }
    
    /**
     * Test of getStockQuotes method with multiple symbols
     */
    @Test
    public void testGetStockQuotes() throws Exception {
        
        // use test json results file
        File file = new File("resources/stockquotedata2results.json");
        URL fileUrl = file.toURI().toURL();
        
        InputStream is = instance.getInputStream(fileUrl);
        
        int stockQuoteCount = instance.getStockQuotes(is).size();
        int expResult = 2;
        
        assertEquals(expResult, stockQuoteCount);
    }
    
    /**
     * Test of getStockQuotes method with null input stream.
     */
    @Test
    public void testGetStockQuotesWithNull() throws Exception {
        
        List<StockQuote> stockQuotes = instance.getStockQuotes(null);
        
        int stockQuoteCount = stockQuotes.size();
        int expResult = 0;
        
        assertEquals(expResult, stockQuoteCount);
    }
}
