package com.stockticker.persistence;

import com.stockticker.User;
import java.sql.Connection;

/**
 * Created by stu on 2/17/14.
 */
public class StockTickerUserDAO {

    private Connection conn;

    public StockTickerUserDAO(Connection conn) {
        this.conn = conn;
    }

    //@Override
    public boolean save(User user) {
        return false;
    }

    //@Override
    public boolean update(User user) {
        return false;
    }

    //@Override
    public boolean findByUserId(int userId) {
        return false;
    }

    //@Override
    public User get(User user) {
        return null;
    }

    //@Override
    public boolean delete(User user) {
        return false;
    }
}
