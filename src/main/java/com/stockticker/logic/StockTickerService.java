package com.stockticker.logic;

import com.stockticker.Stock;
import com.stockticker.StockQuote;
import com.stockticker.User;

import java.util.List;

public interface StockTickerService {

    StockQuote getStockQuote(Stock stock);
    List<StockQuote> getStockQuotes(List<Stock> stocks);
    List<Stock> getTrackedStocks(User user);
    boolean trackStock(User user, Stock stock, boolean tracked);
    boolean isStockTracked(User user, Stock stock);

}
