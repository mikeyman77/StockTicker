package com.stockticker.logic;

import com.stockticker.StockQuote;
import java.io.IOException;
import java.net.MalformedURLException;

import java.util.List;

public interface StockTickerService {

    public List<StockQuote> getStockQuotes(List<String> symbols) 
            throws MalformedURLException, IOException;
    public List<String> getTrackedStocks(String username);
    public boolean trackStock(String username, String symbol, boolean tracked);
    public boolean isStockTracked(String username, String symbol);

}
