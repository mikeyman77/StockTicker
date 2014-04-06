package com.stockticker.logic;

import com.stockticker.StockHistory;
import com.stockticker.StockQuote;
import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.PersistenceServiceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

/**
 * This class provides the basic functionality of getting stock quotes, tracking,
 * and un-tracking stocks for users.
 * 
 * @author Michael Grissom
 */
public enum StockTicker implements StockTickerService {

    /**
     * Instance of stock ticker service.
     */
    INSTANCE;
    
    private static final Logger logger = 
            LogManager.getLogger(StockTicker.class.getName());
    
    private BusinessLogicService bls;
    private PersistenceService persistence;
    private StockQuoteService ysqs;
    private StockHistoryService yshs;

    private StockTicker() {
        PropertyConfigurator.configure("./config/log4j.properties");
    }
    
    // This method is only called by the Business Logic Service
    void start() {
        bls = BusinessLogicService.INSTANCE;
        persistence = bls.getPersistence();
        
        ysqs = YahooStockQuoteService.INSTANCE;
        yshs = YahooStockHistoryService.INSTANCE;
    }
    
    // This method is only called by the Business Logic Service
    void stop() {
        bls = null;
        persistence = null;
        
        ysqs = null;
        yshs = null;
    }
    
    /**
     * This method returns a list of stock quotes based on the stock symbols
     * provided.
     *
     * @param symbols a list of stock symbols
     * @return a list of StockQuote objects
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public List<StockQuote> getStockQuotes(List<String> symbols) 
            throws BusinessLogicException {
        
        return ysqs.getStockQuotes(ysqs.getURL(symbols));
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
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public List<StockHistory> getStockHistory(String symbol, Date startDate, Date endDate) 
            throws BusinessLogicException {
        
        return yshs.getStockHistory(yshs.getURL(symbol, startDate, endDate));
    }
    
    /**
     * This method gets the tracked stocks for a specific user.
     *
     * @param username the username of the user
     * @return a list of tracked stocks
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public List<String> getTrackedStocks(String username) 
            throws BusinessLogicException {
        
        List<String> trackedStocksList = new ArrayList<>();
        
        try {
            trackedStocksList = persistence.getTrackedStocks(username);
        }
        catch (PersistenceServiceException ex) {
            logger.error("Failed to get tracked stocks", ex);
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
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public boolean trackStock(String username, String symbol, boolean tracked) 
            throws BusinessLogicException {
        boolean successful = false;
        
        try {
            successful = persistence.trackStock(username, symbol, tracked);
        }
        catch (PersistenceServiceException ex) {
            logger.error("Failed to track/untrack stock", ex);
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
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public boolean isStockTracked(String username, String symbol) 
            throws BusinessLogicException {
        
        boolean successful = false;
        
        try {
            successful = persistence.isStockTracked(username, symbol);
        }
        catch (PersistenceServiceException ex) {
            logger.error("Failed to get tracked status of stock", ex);
            throw new BusinessLogicException("Error: Could not get if stock is tracked, check logs", ex);
        }
        
        return successful;
    }
}
