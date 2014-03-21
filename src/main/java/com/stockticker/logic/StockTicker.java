package com.stockticker.logic;

import com.stockticker.StockQuote;
import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.StockTickerPersistence;
import java.util.List;


/**
 * This class provides the basic functionality of getting stock quotes, tracking,
 * and un-tracking stocks for users.
 * 
 * @author Michael Grissom
 */
public enum StockTicker implements StockTickerService {
    INSTANCE;
    
    private final PersistenceService persistentence = StockTickerPersistence.INSTANCE;
    private final StockQuoteService ysqs = YahooStockQuoteService.INSTANCE;
    
    /**
     * This method returns a list of stock quotes based on the stock symbols
     * provided.
     *
     * @param symbols a list of stock symbols
     * @return a list of StockQuote objects
     */
    @Override
    public List<StockQuote> getStockQuotes(List<String> symbols) {
        
        return ysqs.getStockQuotes(ysqs
                .getInputStream(ysqs.getURL(symbols)));
    }

    /**
     * This method gets the tracked stocks for a specific user.
     *
     * @param username the username of the user
     * @return a list of tracked stocks
     */
    @Override
    public List<String> getTrackedStocks(String username) {
        return persistentence.getTrackedStocks(username);
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
    public boolean trackStock(String username, String symbol, boolean tracked) {
        return persistentence.trackStock(username, symbol, tracked);
    }
    
    /**
     * This method checks to see if a stock is tracked for a specific user.
     *
     * @param username the username of the user
     * @param symbol the stock symbol
     * @return true if the stock is tracked
     */
    @Override
    public boolean isStockTracked(String username, String symbol) {
        return persistentence.isStockTracked(username, symbol);
    }
}
