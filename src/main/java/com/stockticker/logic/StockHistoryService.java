
package com.stockticker.logic;

import com.stockticker.StockHistory;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * This is the interface that defines the methods provided to retrieve stock
 * history.
 * 
 * @author Michael Grissom
 */
public interface StockHistoryService {
    
    /**
     * This method gets the URL for a stock history query.
     * 
     * @param symbol stock symbol to get history on
     * @param startDate start date for the history (YYYY-MM-DD)
     * @param endDate end date for the history (YYYY-MM-DD)
     * @return a URL object to get stock history data
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public URL getURL(String symbol, Date startDate, Date endDate) 
            throws BusinessLogicException;
    
    /**
     * This method gets the stock history data from the URL.
     * 
     * @param url URL to retrieve the stock history data from
     * @return a list of StockHistory objects
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public List<StockHistory> getStockHistory(URL url) 
            throws BusinessLogicException;
    
}
