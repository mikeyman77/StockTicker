package com.stockticker.logic;

import com.stockticker.StockQuote;

import java.util.List;

public interface StockTickerService {

    public List<StockQuote> getStockQuotes(List<String> symbols);
    public List<String> getTrackedStocks(String username);
    public boolean trackStock(String username, String symbol, boolean tracked);
    public boolean isStockTracked(String username, String symbol);

}
