package com.stockticker.persistence;

import java.util.List;
import java.util.Map;
import com.stockticker.Stock;
import com.stockticker.User;
import com.stockticker.UserInfo;

public interface PersistenceService {
    public List<Stock> getTrackedStocks(User user);
    public boolean trackStock(User user, Stock stock, boolean track);
    public boolean isStockTracked(User user, Stock stock);
    public boolean userExists(User user);
    public User createUser(User user);
    public boolean updateUser(User user);
    public User    getUser(User user);
    public boolean deleteUser(User user);
    public boolean isLoggedIn(String username);
    public boolean setLoginStatus(String username, boolean status);
    public List<String> getLoggedInUsers();
    public UserInfo getUserInfo(String username);
}