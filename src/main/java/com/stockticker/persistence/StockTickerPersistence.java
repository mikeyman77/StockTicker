package com.stockticker.persistence;

import java.util.ArrayList;
import java.util.List;
import com.stockticker.Stock;
import com.stockticker.User;

public enum StockTickerPersistence implements PersistenceService {
    INSTANCE;

    private List<Stock> stocks = new ArrayList<Stock>();
    private List<User> users = new ArrayList<User>();

    @Override
    public List<Stock> getTrackedStocks(User user) {
        return stocks;
    }

    @Override
    public boolean trackStock(User user, Stock stock, boolean track) {
        if (!stocks.contains(stock))
            stocks.add(stock);
        return true;
    }

    @Override
    public boolean isStockTracked(User user, Stock stock) {
        if (stocks.contains(stock))
            return true;
        else
            return false;
    }

    @Override
    public boolean userExists(User user) {
        if (users.contains(user))
            return true;
        else
            return false;
    }

    @Override
    public boolean saveUser(User user) {
        int i = users.indexOf(user);
        if (i >= 0)
            users.set(i, user);
        else
            users.add(user);

        return true;
    }

    @Override
    public User loadUser(User user) {
        int i = users.indexOf(user);
        if (i >= 0)
            return users.get(i);
        else
            return null;
    }

    @Override
    public boolean deleteUser(User user) {
        int i = users.indexOf(user);
        if (i >= 0) {
            users.remove(i);
            return true;
        }
        return false;
    }

}