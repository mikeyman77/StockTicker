package com.stockticker.persistence;

import java.util.List;
import com.stockticker.*;

public enum StockTickerPersistence implements PersistenceService {
    INSTANCE;

    public List<Stock> getTrackedStocks(User user) {
        return (List<Stock>)null;
    }

    public boolean trackStock(User user, Stock stock, boolean track) {
        return false;
    }

    public boolean isStockTracked(User user, Stock stock) {
        return false;
    }

    public boolean saveUser(User user) {
        return false;
    }

    public boolean loadUser(User user) {
        return false;
    }

    public boolean deleteUser(User user) {
        return false;
    }
}