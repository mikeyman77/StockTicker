package com.stockticker.logic;

import com.stockticker.StockQuote;
import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.StockTickerPersistence;
import java.io.File;
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
     */
    @Override
    public List<StockQuote> getStockQuotes(List<String> symbols) {
        
        return ysqs.getStockQuotes(ysqs
                .getInputStream(ysqs.getURL(symbols)));
    }
    
    /**
     * This method returns a list of stock quotes based on the file provided.
     */
    @Override
    public List<StockQuote> getStockQuotes(File file) {
        
        return ysqs.getStockQuotes(ysqs
                .getInputStream(file));
    }
    
    /**
     * This method gets the tracked stocks for a specific user.
     */
    @Override
    public List<String> getTrackedStocks(String username) {
        return persistentence.getTrackedStocks(username);
    }
    
    /**
     * This method tracks and un-tracks stocks for a specific user.
     */
    @Override
    public boolean trackStock(String username, String symbol, boolean tracked) {
        return persistentence.trackStock(username, symbol, tracked);
    }
    
    /**
     * This method checks to see if a stock is tracked for a specific user.
     */
    @Override
    public boolean isStockTracked(String username, String symbol) {
        return persistentence.isStockTracked(username, symbol);
    }
}
