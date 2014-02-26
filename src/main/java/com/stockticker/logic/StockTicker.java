package com.stockticker.logic;

import com.stockticker.StockQuote;

import java.util.List;

public enum StockTicker implements StockTickerService {
    INSTANCE;

    @Override
    public List<StockQuote> getStockQuotes(List<String> symbols) {
        return null;
    }

    @Override
    public List<String> getTrackedStocks(String username) {
        return null;
    }

    @Override
    public boolean trackStock(String username, String symbol, boolean tracked) {
        return false;
    }

    @Override
    public boolean isStockTracked(String username, String symbol) {
        return false;
    }
}
