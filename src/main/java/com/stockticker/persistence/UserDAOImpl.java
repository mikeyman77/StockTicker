package com.stockticker.persistence;

import com.stockticker.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author: Stuart Connall
 * @version 1.0 2/17/14.
 */
public class UserDAOImpl {

    private Connection connection;

    public UserDAO(Connection conn) {
        this.connection = conn;
    }

    public User create(String username, String password){
        User user = null;
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery("");
        while (rs.next()) {
            System.out.println(rs.getString("name"));
        }


        return user;
    }

    public boolean update(User user) {

    }

    public boolean findByUserId(int userId) {

    }

    public User    get(User user) {

    }

    public boolean delete(User user) {

    }
}
