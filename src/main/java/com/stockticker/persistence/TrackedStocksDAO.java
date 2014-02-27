package com.stockticker.persistence;

import com.stockticker.Stock;
import com.stockticker.User;
import java.util.List;

/**
 * Interface that defines the Data Access methods for the
 * tracking of stocks for users
 */
public interface TrackedStocksDAO {

    public boolean create(String username, String stock);
    public boolean exists(String username, String stock);
    public boolean update(String username, String stock);
    public List<String> get(String username);
    public boolean delete(String username, String stock);
}
