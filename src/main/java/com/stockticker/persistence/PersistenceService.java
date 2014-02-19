package com.stockticker.persistence;

import java.util.List;
import com.stockticker.Stock;
import com.stockticker.User;

public interface PersistenceService {
    public List<Stock> getTrackedStocks(User user);
    public boolean trackStock(User user, Stock stock, boolean track);
    public boolean isStockTracked(User user, Stock stock);
    public boolean userExists(User user);
    public User createUser(User user);
    public boolean updateUser(User user);
    public User    loadUser(User user);
    public boolean deleteUser(User user);
    public boolean setLoginStatus(User user);
}