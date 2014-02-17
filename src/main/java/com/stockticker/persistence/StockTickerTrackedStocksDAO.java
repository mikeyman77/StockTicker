package com.stockticker.persistence;

import com.stockticker.Stock;
import com.stockticker.User;
import java.sql.Connection;
import java.util.List;

/**
 * Created by stu on 2/17/14.
 */
public class StockTickerTrackedStocksDAO implements TrackedStocksDAO {

    private Connection conn;

    public StockTickerTrackedStocksDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(User user, Stock stock) {
        return false;
    }

    @Override
    public boolean update(User user, Stock stock) {
        return false;
    }

    @Override
    public boolean findByStockName(User user, String symbol) {
        return false;
    }

    @Override
    public List<Stock> get(User user) {
        return null;
    }

    @Override
    public boolean delete(User user, Stock stock) {
        return false;
    }
}
