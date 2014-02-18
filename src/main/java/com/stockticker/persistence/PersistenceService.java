package com.stockticker.persistence;

import java.util.List;
import com.stockticker.Stock;
import com.stockticker.User;

public interface PersistenceService {
    public List<Stock> getTrackedStocks(String username);
    public boolean trackStock(String username, Stock stock, boolean track);
    public boolean isStockTracked(String username, String symbol);
    public boolean userExists(String username);
    public User createUser(String username, String password);
    public boolean updateUser(User user);
    public User    loadUser(String username);
    public boolean deleteUser(String username);
}