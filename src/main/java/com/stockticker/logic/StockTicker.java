package com.stockticker.logic;

import com.stockticker.Stock;
import com.stockticker.StockQuote;

import java.util.List;

public enum StockTicker implements StockTickerService {
    INSTANCE;

    @Override
    public StockQuote getStockQuote(Stock stock) {
        return null;
    }

    @Override
    public List<StockQuote> getStockQuotes(List<Stock> stocks) {
        return null;
    }

    @Override
    public List<Stock> getTrackedStocks(String username) {
        return null;
    }

    @Override
    public boolean trackStock(String username, Stock stock, boolean tracked) {
        return false;
    }

    @Override
    public boolean isStockTracked(String username, Stock stock) {
        return false;
    }
}
