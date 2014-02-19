package com.stockticker.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.stockticker.Stock;


/**
 * Created by stu on 2/18/14.
 */
public class TrackedStocks {

    private String userName;
    private Map<String, Stock> trackedStocks;

    public TrackedStocks(String username, Stock stock) {
        this.userName = userName;
        trackedStocks = new TreeMap<String, Stock>();
        trackedStocks.put(stock.getSymbol(), stock);
    }

    public void put(Stock stock) {
        trackedStocks.put(stock.getSymbol(), stock);
    }

    public void remove(Stock stock) {
        trackedStocks.remove(stock.getSymbol());
    }

    public List<Stock> getStocks() {
        return new ArrayList<Stock>(trackedStocks.values());
    }

    public String[] getStockNames() {
        return (trackedStocks.keySet()).toArray(new String[0]);
    }

    public boolean isStockTracked(String symbol) {
        if (trackedStocks.containsKey(symbol))
            return true;
        else
            return false;
    }
}
