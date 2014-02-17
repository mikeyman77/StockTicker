package com.stockticker.persistence;

import com.stockticker.Stock;
import com.stockticker.User;
import java.util.List;

/**
 * Created by stu on 2/17/14.
 */
public interface TrackedStocksDAO {

    public boolean save(User user, Stock stock);
    public boolean update(User user, Stock stock);
    public boolean findByStockName(User user, String symbol);
    public List<Stock> get(User user);
    public boolean delete(User user, Stock stock);
}
