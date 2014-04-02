
package com.stockticker.logic;

import com.stockticker.StockHistory;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the YagooStockHistoryService class.
 * 
 * @author mikeyman77
 */
public class YahooStockHistoryServiceTest {
    
    private YahooStockHistoryService instance = YahooStockHistoryService.INSTANCE;
    
    
    /**
     * Test of getURL method with a valid symbol, start date and end date.
     */
    @Test
    public void testGetURLWithValidArguments() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String symbol = "GOOG";
        Date startDate = dateFormat.parse("2014-03-11");
        Date endDate = dateFormat.parse("2014-03-12");
        URL result = instance.getURL(symbol, startDate, endDate);
        boolean containsSymbol = result.toString().contains(symbol);
        boolean containsStartDate = result.toString().contains(dateFormat.format(startDate));
        boolean containsEndDate = result.toString().contains(dateFormat.format(endDate));
        assertTrue("Testing getURL", (containsSymbol && containsStartDate 
                                        && containsEndDate));
    }
    
    /**
     * Test of getURL method with an invalid symbol.
     */
    @Test (expected=BusinessLogicException.class)
    public void testGetURLWithInvalidSymbol() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String symbol = "XXXX";
        Date startDate = dateFormat.parse("2014-03-11");
        Date endDate = dateFormat.parse("2014-03-12");
        URL result = instance.getURL(symbol, startDate, endDate);
    }
    
    /**
     * Test of getURL method with an empty symbol.
     */
    @Test (expected=BusinessLogicException.class)
    public void testGetURLWithEmptySymbol() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String symbol = "";
        Date startDate = dateFormat.parse("2014-03-11");
        Date endDate = dateFormat.parse("2014-03-12");
        URL result = instance.getURL(symbol, startDate, endDate);
    }
    
    /**
     * Test of getURL method with a null symbol.
     */
    @Test (expected=BusinessLogicException.class)
    public void testGetURLWithNullSymbol() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse("2014-03-11");
        Date endDate = dateFormat.parse("2014-03-12");
        URL result = instance.getURL(null, startDate, endDate);
    }
    
    /**
     * Test of getURL method with a null start date.
     */
    @Test (expected=BusinessLogicException.class)
    public void testGetURLWithNullStartDate() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String symbol = "GOOG";
        Date endDate = dateFormat.parse("2014-03-12");
        URL result = instance.getURL(symbol, null, endDate);
    }
    
    /**
     * Test of getURL method with a null end date.
     */
    @Test (expected=BusinessLogicException.class)
    public void testGetURLWithNullEndDate() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String symbol = "GOOG";
        Date startDate = dateFormat.parse("2014-03-11");
        URL result = instance.getURL(symbol, startDate, null);
    }

    /**
     * Test of getStockHistory method with a invalid URL (null).
     */
    @Test
    public void testGetStockHistoryWithInvalidURL() throws MalformedURLException {
        List<StockHistory> result = instance.getStockHistory(null);
        int expected = 0;
        int resultCount = result.size();
        assertEquals(expected, resultCount);
    }
    
    /**
     * Test of getStockHistory method with a valid URL for zero results.
     */
    @Test
    public void testGetStockHistoryWithZeroResults() throws MalformedURLException {
        File file = new File("resources/stockhistorydata0results.json");
        URL fileUrl = file.toURI().toURL();
        
        List<StockHistory> result = instance.getStockHistory(fileUrl);
        int expected = 0;
        int resultCount = result.size();
        assertEquals(expected, resultCount);
    }
    
    /**
     * Test of getStockHistory method with a valid URL for one result.
     */
    @Test
    public void testGetStockHistoryWithOneResult() throws MalformedURLException {
        File file = new File("resources/stockhistorydata1result.json");
        URL fileUrl = file.toURI().toURL();
        
        List<StockHistory> result = instance.getStockHistory(fileUrl);
        int expected = 1;
        int resultCount = result.size();
        assertEquals(expected, resultCount);
    }
    
    /**
     * Test of getStockHistory method with a valid URL for two results.
     */
    @Test
    public void testGetStockHistoryWithTwoResults() throws MalformedURLException {
        File file = new File("resources/stockhistorydata2results.json");
        URL fileUrl = file.toURI().toURL();
        
        List<StockHistory> result = instance.getStockHistory(fileUrl);
        int expected = 2;
        int resultCount = result.size();
        assertEquals(expected, resultCount);
    }
}
