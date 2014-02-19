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
    private Map<String,TrackedStocks> trackedStocksMap = new TreeMap<String,TrackedStocks>();
    private Map<String,User> usersMap = new TreeMap<String, User>();
    private int userId = 0;

    @Override
    public List<Stock> getTrackedStocks(User user) {
        TrackedStocks tracked = trackedStocksMap.get(user.getUserName());
        List<Stock> stocks = new ArrayList<Stock>(tracked.getStocks());
        return stocks;
    }

    @Override
    public boolean trackStock(User user, Stock stock, boolean track) {
            TrackedStocks tracked = trackedStocksMap.get(user.getUserName());
            if (track) {
                if (tracked != null) {
                    tracked.put(stock);
                }
                else {
                    tracked = new TrackedStocks(user.getUserName());
                    trackedStocksMap.put(user.getUserName(), tracked);
                }
            }
            else {
                if (tracked != null) {
                    tracked.remove(stock);
                }
            }
        return true;
    }

    @Override
    public boolean isStockTracked(User user, Stock stock) {
        TrackedStocks tracked = trackedStocksMap.get(user.getUserName());

        return tracked.isStockTracked(stock.getSymbol());
    }

    @Override
    public boolean userExists(User user) {
        if (usersMap.containsKey(user.getUserName()))
            return true;
        else
            return false;
    }

    @Override
    public User createUser(User user) {
        if (!usersMap.containsKey(user.getUserName())) {
            userId++;
            user.setUserID(userId);
            usersMap.put(user.getUserName(), user);
        }
        return user;
    }

    @Override
    public boolean updateUser(User user) {
        usersMap.put(user.getUserName(), user);

        return true;
    }

    @Override
    public User loadUser(User user) {
        return usersMap.get(user.getUserName());
    }

    @Override
    public boolean deleteUser(User user) {
        if (usersMap.remove(user.getUserName()) != null)
            return true;
        else
            return false;
    }

    @Override
    public boolean setLoginStatus(User user) {
        if (usersMap.containsKey(user.getUserName()))
            usersMap.put(user.getUserName(), user);
        else
            return false;

        return true;
    }

}