package com.stockticker.persistence;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import com.stockticker.Stock;
import com.stockticker.User;
import com.stockticker.TrackedStocks;

public enum StockTickerPersistence implements PersistenceService {
    INSTANCE;


    private Map<String,Stock> stocksMap = new TreeMap<String,Stock>();
    private Map<String,TrackedStocks> trackedStocksMap = new TreeMap<String,TrackedStocks>();
    private Map<String,User> usersMap = new TreeMap<String, User>();
    private int userId = 0;

    @Override
    public List<Stock> getTrackedStocks(String username) {
        TrackedStocks tracked = trackedStocksMap.get(username);
        List<Stock> stocks = new ArrayList<Stock>(tracked.getStocks());
        return stocks;
    }

    @Override
    public boolean trackStock(String username, Stock stock, boolean track) {
            TrackedStocks tracked = trackedStocksMap.get(username);
            if (track) {
                tracked.put(stock);
            }
            else {
                tracked.remove(stock);
            }
        return true;
    }

    @Override
    public boolean isStockTracked(String username, String symbol) {
        TrackedStocks tracked = trackedStocksMap.get(username);

        return tracked.isStockTracked(symbol);
    }

    @Override
    public boolean userExists(String username) {
        if (usersMap.containsKey(username))
            return true;
        else
            return false;
    }

    @Override
    public User createUser(String username, String password) {
        User user = null;
        if (!usersMap.containsKey(username)) {
            userId++;
            user = new User(username, password);
            user.setUserID(userId);
            usersMap.put(username, user);
        }
        return user;
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