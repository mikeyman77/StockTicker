package com.stockticker.logic;

import com.stockticker.Stock;
import com.stockticker.StockQuote;
import com.stockticker.User;
import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.StockTickerPersistence;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import java.util.List;


public enum StockTicker implements StockTickerService {
    INSTANCE;
    
    private final PersistenceService persistentence = StockTickerPersistence.INSTANCE;
    private final StockQuoteService yahooStockQuoteService = YahooStockQuoteService.INSTANCE;
    
    @Override
    public List<StockQuote> getStockQuotes(List<String> symbols) 
            throws MalformedURLException, IOException {
        
        if (!yahooStockQuoteService.constructURL(symbols)) {
            // url construction failed, return empty list
            return new ArrayList<>();
        }
        
        return yahooStockQuoteService.getStocks();
    }

    @Override
    public List<String> getTrackedStocks(String username) {
        return persistentence.getTrackedStocks(username);
    }

    @Override
    public boolean trackStock(String username, String symbol, boolean tracked) {
        return persistentence.trackStock(username, symbol, tracked);
    }

    @Override
    public boolean isStockTracked(String username, String symbol) {
        return persistentence.isStockTracked(username, symbol);
    }
}
