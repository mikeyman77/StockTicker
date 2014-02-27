package com.stockticker.persistence;

import com.stockticker.Stock;
import com.stockticker.User;
import java.sql.Connection;
import java.util.List;

/**
 * Created by stu on 2/17/14.
 */
public class TrackedStocksDAOImpl implements TrackedStocksDAO {

    private Connection connection;

    public TrackedStocksDAOImpl(Connection conn) {
        this.connection = conn;
    }

    @Override
    public boolean create(String username, String stock) {
        return false;
    }

    @Override
    public boolean exists(String username, String stock) {
        return false;
    }

    @Override
    public boolean update(String username, String stock) {
        return false;
    }


    @Override
    public List<String> get(String username) {
        return null;
    }

    @Override
    public boolean delete(String username, String stock) {
        return false;
    }
}
