package com.stockticker.logic;

import com.stockticker.StockHistory;
import com.stockticker.StockQuote;
import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.PersistenceServiceException;
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
    
    private BusinessLogicService bls;
    private PersistenceService persistence;
    private StockQuoteService ysqs;
    private StockHistoryService yshs;
    
    // This method is only called by the Business Logic Service
    void start() {
        bls = BusinessLogicService.INSTANCE;
        persistence = bls.getPersistence();
        
        ysqs = YahooStockQuoteService.INSTANCE;
        yshs = YahooStockHistoryService.INSTANCE;
    }
    
    @Override
    public List<StockQuote> getStockQuotes(List<String> symbols) 
            throws BusinessLogicException {
        
        List<StockQuote> stockQuoteList = new ArrayList<>();

        stockQuoteList = ysqs.getStockQuotes(ysqs.getURL(symbols));
        
        return stockQuoteList;
    }
    
    @Override
    public List<StockHistory> getStockHistory(String symbol, Date startDate, Date endDate) 
            throws BusinessLogicException {
        
        List<StockHistory> stockHistoryList = new ArrayList<>();

        stockHistoryList = yshs.getStockHistory(yshs.getURL(symbol, startDate, endDate));
        
        return stockHistoryList;
    }
    
    @Override
    public List<String> getTrackedStocks(String username) 
            throws BusinessLogicException {
        
        List<String> trackedStocksList = new ArrayList<>();
        
        try {
            trackedStocksList = persistence.getTrackedStocks(username);
        }
        catch (PersistenceServiceException ex) {
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
        catch (PersistenceServiceException ex) {
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
        catch (PersistenceServiceException ex) {
            throw new BusinessLogicException("Error: Could not get if stock is tracked, check logs", ex);
        }
        
        return successful;
    }
}
