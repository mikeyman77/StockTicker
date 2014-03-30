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
