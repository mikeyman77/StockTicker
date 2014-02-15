package com.stockticker.persistence;

import java.util.List;
import com.stockticker.Stock;
import com.stockticker.User;

public enum StockTickerPersistence implements PersistenceService {
    INSTANCE;
    
    private String name;

    @Override
    public List<Stock> getTrackedStocks(User user) {
        return null;
    }

    @Override
    public boolean trackStock(User user, Stock stock, boolean track) {
        return false;
    }

    @Override
    public boolean isStockTracked(User user, Stock stock) {
        return false;
    }

    @Override
    public boolean saveUser(User user) {
        return false;
    }

    @Override
    public boolean loadUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }

}