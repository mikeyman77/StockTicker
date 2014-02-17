package com.stockticker.persistence;

import com.stockticker.User;

/**
 * Created by stu on 2/17/14.
 */
public interface UserDAO {

    public boolean save(User user);
    public boolean update(User user);
    public boolean findByUserId(int userId);
    public User    get(User user);
    public boolean delete(User user);
}
