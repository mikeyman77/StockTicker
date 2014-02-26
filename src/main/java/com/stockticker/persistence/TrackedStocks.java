package com.stockticker.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import com.stockticker.Stock;


/**
 * Created by stu on 2/18/14.
 */
public class TrackedStocks {

    private String userName;
    private List<String> trackedStocks;

    public TrackedStocks(String username) {
        this.userName = userName;
        trackedStocks = new ArrayList<String>();
    }

    public boolean put(String stock) {
        if (stock.equals(""))
            return false;

        trackedStocks.add(stock);
        return true;
    }

    public boolean remove(String stock) {
        if (stock.equals(""))
            return false;

        return trackedStocks.remove(stock);
    }

    public List<String> getStocks() {
        return trackedStocks;
    }

    public boolean isStockTracked(String stock) {
        if (trackedStocks.contains(stock))
            return true;
        else
            return false;
    }
}
