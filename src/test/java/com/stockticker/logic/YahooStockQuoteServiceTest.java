
package com.stockticker.logic;

import com.stockticker.StockQuote;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
    private final String jsonStockString = "{\"query\":{\"count\":2,\"created\""
            + ":\"2014-02-27T11:48:41Z\",\"lang\":\"en-US\",\"results\""
            + ":{\"quote\":[{\"Bid\":\"1213.9399\",\"Change\":\"+0.17\",\"LastTradeDate\""
            + ":\"2/26/2014\",\"EarningsShare\":\"38.021\",\"DaysLow\""
            + ":\"1213.76\",\"DaysHigh\":\"1228.88\",\"YearLow\""
            + ":\"761.26\",\"YearHigh\":\"1228.88\",\"MarketCapitalization\""
            + ":\"410.0B\",\"LastTradePriceOnly\":\"1220.17\",\"Name\""
            + ":\"Google Inc.\",\"Open\":\"1224.00\",\"PreviousClose\""
            + ":\"1220.00\",\"ChangeinPercent\":\"+0.01%\",\"PERatio\""
            + ":\"32.09\",\"Symbol\":\"GOOG\",\"LastTradeTime\":\"4:00pm\",\"Volume\""
            + ":\"1984047\",\"Ask\":\"1229.99\",\"AverageDailyVolume\""
            + ":\"2092520\"},{\"Bid\":\"514.46\",\"Change\":\"-4.71\",\"LastTradeDate\""
            + ":\"2/26/2014\",\"EarningsShare\":\"40.233\",\"DaysLow\""
            + ":\"515.60\",\"DaysHigh\":\"525.00\",\"YearLow\""
            + ":\"385.10\",\"YearHigh\":\"575.14\",\"MarketCapitalization\""
            + ":\"461.5B\",\"LastTradePriceOnly\":\"517.35\",\"Name\""
            + ":\"Apple Inc.\",\"Open\":\"523.61\",\"PreviousClose\""
            + ":\"522.06\",\"ChangeinPercent\":\"-0.90%\",\"PERatio\""
            + ":\"12.98\",\"Symbol\":\"AAPL\",\"LastTradeTime\":\"4:00pm\",\"Volume\""
            + ":\"9880581\",\"Ask\":\"514.88\",\"AverageDailyVolume\""
            + ":\"12711800\"}]}}}";
    
    /**
     * Test of getURL method with one valid symbol.
     */
    @Test
    public void testGetURL() throws Exception {
        List<String> symbols = new ArrayList<>();
        symbols.add("GOOG");

        assertNotNull(instance.getURL(symbols));
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
     * Test of getStockQuotes method with multiple symbols
     */
    @Test
    public void testGetStockQuotesWithString() throws Exception {
        
        // use json string
        InputStream is = new ByteArrayInputStream(jsonStockString.getBytes());
        
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
