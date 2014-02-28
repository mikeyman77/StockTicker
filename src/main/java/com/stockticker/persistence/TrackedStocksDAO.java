package com.stockticker.persistence;

import com.stockticker.Stock;
import com.stockticker.User;
import java.util.List;

/**
 * Interface that defines the Data Access methods for the
 * tracking of stocks that users are interested in.
 *
 * @author Stuart Connall
 * @version 1.0 02/27/2014
 */
public interface TrackedStocksDAO {

    public int     getStockId(String stock);
    public boolean add(int userId, int stockId);
    public boolean exists(int userId, int stockId);
    public List<String> get(int userId);
    public boolean delete(int userId, int stockId);
    public boolean deleteAll(int userId);
}
