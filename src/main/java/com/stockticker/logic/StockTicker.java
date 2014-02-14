package com.stockticker.logic;

import com.stockticker.Stock;
import com.stockticker.StockQuote;
import com.stockticker.User;

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
    public List<Stock> getTrackedStocks(User user) {
        return null;
    }

    @Override
    public boolean trackStock(User user, Stock stock, boolean tracked) {
        return false;
    }

    @Override
    public boolean isStockTracked(User user, Stock stock) {
        return false;
    }

}
