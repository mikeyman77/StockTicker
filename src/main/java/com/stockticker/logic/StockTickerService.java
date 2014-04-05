package com.stockticker.logic;

import com.stockticker.StockHistory;
import com.stockticker.StockQuote;
import java.util.Date;
import java.util.List;

/**
 * This interface defines the functionality to get tracked stocks. It can also 
 * get the current tracked stocks of a user, track and un track stocks, and 
 * see if a specific stock is tracked by a specific user.
 * 
 * @author Michael Grissom
 */
public interface StockTickerService {
    
    /**
     * This method returns a list of stock quotes based on the stock symbols
     * provided.
     *
     * @param symbols a list of stock symbols
     * @return a list of StockQuote objects
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public List<StockQuote> getStockQuotes(List<String> symbols) 
            throws BusinessLogicException;
    
    /**
     * This method returns a list of stock history based on the stock symbol, 
     * start date and end date provided. This method provides one method call 
     * instead of two in order to get the stock history
     *
     * @param symbol stock symbol to get history for
     * @param startDate stock history start date
     * @param endDate stock history end date
     * @return a list of StockHistory objects
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public List<StockHistory> getStockHistory(String symbol, Date startDate, Date endDate) 
            throws BusinessLogicException;
    
    /**
     * This method gets the tracked stocks for a specific user.
     *
     * @param username the username of the user
     * @return a list of tracked stocks
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public List<String> getTrackedStocks(String username) 
            throws BusinessLogicException;
    
    /**
     * This method tracks and un-tracks stocks for a specific user.
     *
     * @param username the username of the user
     * @param symbol the stock symbol
     * @param tracked true to track stock and false to not track the stock
     * @return true if the operation was successful
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public boolean trackStock(String username, String symbol, boolean tracked) 
            throws BusinessLogicException;
    
    /**
     * This method checks to see if a stock is tracked for a specific user.
     *
     * @param username the username of the user
     * @param symbol the stock symbol
     * @return true if the stock is tracked
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public boolean isStockTracked(String username, String symbol) 
            throws BusinessLogicException;

}
