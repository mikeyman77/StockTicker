package com.stockticker.logic;

import com.stockticker.StockHistory;
import com.stockticker.StockQuote;
import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.StockTickerPersistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * This class provides the basic functionality of getting stock quotes, tracking,
 * and un-tracking stocks for users.
 * 
 * @author Michael Grissom
 */
public enum StockTicker implements StockTickerService {
    INSTANCE;
    
    private final PersistenceService persistence = StockTickerPersistence.INSTANCE;
    private final StockQuoteService ysqs = YahooStockQuoteService.INSTANCE;
    private final StockHistoryService yshs = YahooStockHistoryService.INSTANCE;
    
    /**
     * This method returns a list of stock quotes based on the stock symbols
     * provided.
     *
     * @param symbols a list of stock symbols
     * @return a list of StockQuote objects
     */
    @Override
    public List<StockQuote> getStockQuotes(List<String> symbols) 
            throws BusinessLogicException {
        
        List<StockQuote> stockQuoteList = new ArrayList<>();
        
        try {
            stockQuoteList = ysqs.getStockQuotes(ysqs.getURL(symbols));
        }
        catch (BusinessLogicException ex) {
            throw new BusinessLogicException("Error: Could not get stock quotes, check logs", ex);
        }
        
        return stockQuoteList;
    }
    
    /**
     * This method returns a list of stock history based on the stock symbol, 
     * start date and end date provided. This method provides one method call 
     * instead of two in order to get the stock history
     *
     * @param symbol stock symbol to get history for
     * @param startDate stock history start date
     * @param endDate stock history end date
     * @return a list of StockHistory objects
     */
    @Override
    public List<StockHistory> getStockHistory(String symbol, Date startDate, Date endDate) 
            throws BusinessLogicException {
        
        List<StockHistory> stockHistoryList = new ArrayList<>();
        
        try {
            stockHistoryList = yshs.getStockHistory(yshs.getURL(symbol, startDate, endDate));
        }
        catch (BusinessLogicException ex) {
            throw new BusinessLogicException("Error: Could not get stock quotes, check logs", ex);
        }
        
        return stockHistoryList;
    }
    
    /**
     * This method gets the tracked stocks for a specific user.
     *
     * @param username the username of the user
     * @return a list of tracked stocks
     */
    @Override
    public List<String> getTrackedStocks(String username) 
            throws BusinessLogicException {
        
        List<String> trackedStocksList = new ArrayList<>();
        
        try {
            trackedStocksList = persistence.getTrackedStocks(username);
        }
        catch (BusinessLogicException ex) {
            throw new BusinessLogicException("Error: Could not get tracked stocks, check logs", ex);
        }
        
        return trackedStocksList;
    }
    
    /**
     * This method tracks and un-tracks stocks for a specific user.
     *
     * @param username the username of the user
     * @param symbol the stock symbol
     * @param tracked true to track stock and false to not track the stock
     * @return true if the operation was successful
     */
    @Override
    public boolean trackStock(String username, String symbol, boolean tracked) 
            throws BusinessLogicException {
        boolean successful = false;
        
        try {
            successful = persistence.trackStock(username, symbol, tracked);
        }
        catch (BusinessLogicException ex) {
            throw new BusinessLogicException("Error: Could not tracked/untrack stock, check logs", ex);
        }
        
        return successful;
    }
    
    /**
     * This method checks to see if a stock is tracked for a specific user.
     *
     * @param username the username of the user
     * @param symbol the stock symbol
     * @return true if the stock is tracked
     */
    @Override
    public boolean isStockTracked(String username, String symbol) 
            throws BusinessLogicException {
        
        boolean successful = false;
        
        try {
            successful = persistence.isStockTracked(username, symbol);
        }
        catch (BusinessLogicException ex) {
            throw new BusinessLogicException("Error: Could not get if stock is tracked, check logs", ex);
        }
        
        return successful;
    }
}
