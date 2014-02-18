package com.stockticker.logic;

import com.stockticker.Stock;
import com.stockticker.StockQuote;

import java.util.List;

public interface StockTickerService {

    StockQuote getStockQuote(Stock stock);
    List<StockQuote> getStockQuotes(List<Stock> stocks);
    List<Stock> getTrackedStocks(String username);
    boolean trackStock(String username, Stock stock, boolean tracked);
    boolean isStockTracked(String username, Stock stock);

}
