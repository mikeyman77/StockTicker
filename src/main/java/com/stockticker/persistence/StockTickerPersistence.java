package com.stockticker.persistence;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import com.stockticker.Stock;
import com.stockticker.User;

public enum StockTickerPersistence implements PersistenceService {
    INSTANCE;

    private Map<String,Stock> stocksMap = new TreeMap<String,Stock>();
    private Map<String,User> usersMap = new TreeMap<String,User>();
    private List<String> usernames = new ArrayList<String>();

    @Override
    public List<Stock> getTrackedStocks(String username) {
        List<Stock> stocks = new ArrayList<Stock>(stocksMap.values());
        return stocks;
    }

    @Override
    public boolean trackStock(String username, Stock stock, boolean track) {
        if (!stocksMap.containsKey(stock.getSymbol()))
            stocksMap.put(stock.getSymbol(), stock);
        return true;
    }

    @Override
    public boolean isStockTracked(String username, String symbol) {
        if (stocksMap.get(symbol) != null)
            return true;
        else
            return false;
    }

    @Override
    public boolean userExists(String username) {
        if (usersMap.containsKey(username))
            return true;
        else
            return false;
    }

    @Override
    public boolean updateUser(User user) {
        usersMap.put(user.getUserName(), user);

        return true;
    }

    @Override
    public User loadUser(String username) {
        return usersMap.get(username);
    }

    @Override
    public boolean deleteUser(String username) {
        if (usersMap.remove(username) != null)
            return true;
        else
            return false;
    }

}