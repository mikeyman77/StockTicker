package com.stockticker.logic;

import com.stockticker.StockQuote;
import java.util.List;

/**
 * This interface defines the functionality to get tracked stocks. It can also 
 * get the current tracked stocks of a user, track and un track stocks, and 
 * see if a specific stock is tracked by a specific user.
 * 
 * @author Michael Grissom
 */
public interface StockTickerService {
    
    public List<StockQuote> getStockQuotes(List<String> symbols);
    public List<String> getTrackedStocks(String username);
    public boolean trackStock(String username, String symbol, boolean tracked);
    public boolean isStockTracked(String username, String symbol);

}
