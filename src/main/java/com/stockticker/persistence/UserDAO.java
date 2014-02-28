package com.stockticker.persistence;

import com.stockticker.User;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * @author -  Stuart Connall
 * @version - 1.0 02/25/2014
 */
public interface UserDAO {

    public int getUserId(String username);
    public User create(String username, String password);
    public boolean exists(String username);
    public boolean update(User user);
    public User findByUserId(int userId);
    public User    get(String username);
    public boolean delete(String username);
    public boolean isLoggedIn(String username);
    public boolean setLoginStatus(String username, boolean status);
    public List<String> getLoggedInUsers();

}
