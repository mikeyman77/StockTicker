package com.stockticker.persistence;

import java.util.List;
import java.util.Map;
import com.stockticker.Stock;
import com.stockticker.User;
import com.stockticker.UserInfo;

public interface PersistenceService {
    public List<String> getTrackedStocks(String username);
    public boolean trackStock(String username, String stock, boolean track);
    public boolean isStockTracked(String username, String stock);
    public boolean userExists(String username);
    public User createUser(String username, String password);
    public boolean updateUser(User user);
    public User    getUser(String username);
    public boolean deleteUser(String username);
    public boolean isLoggedIn(String username);
    public boolean setLoginStatus(String username, boolean status);
    public List<String> getLoggedInUsers();
    public UserInfo getUserInfo(String username);
}