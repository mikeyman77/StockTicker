package com.stockticker.persistence;

import com.stockticker.User;
import com.stockticker.UserInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Stuart Connall
 * @version 1.0 2/17/14.
 */
public class UserDAOImpl implements UserDAO {

    private Connection connection;

    public UserDAOImpl(Connection conn) {
        this.connection = conn;
    }

    /**
     * Gets the userId associated with the specified user name
     *
     * @param username the name of the user
     * @return the user id, -1 otherwise
     */
    public int getUserId(String username) {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT userId FROM user WHERE username='"+username+"'");
            if (result.next())
                return result.getInt(1);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    public User create(String username, String password){
        User user = null;
        ResultSet rs;

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT username FROM user WHERE username='"+username+"'");
            if (result.next())
                return null;

            //Create an empty userinfo row and retrieve the auto incremented row id
            statement.executeUpdate("INSERT INTO userinfo (firstName) VALUES ('')");
            result = statement.executeQuery("CALL IDENTITY();");
            int id = -1;
            if (result.next())
                id = result.getInt(1);

            //insert the user row with username, password, and id from userinfo table insert
            statement.executeUpdate("INSERT INTO user (FK_userInfoId, username, password, joinedDate, isLoggedIn) VALUES ("+
                                    id+",'"+username+"','"+password+"','"+new Timestamp(System.currentTimeMillis())+"','FALSE')");
            result = statement.executeQuery("CALL IDENTITY();");
            if (result.next())
                id = result.getInt(1);

            //create and return new User object
            user = new User(username, password);
            user.setUserID(id);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public boolean exists(String username) {

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT username FROM user WHERE username='"+username+"'");
            if (result.next())
                return true;
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean update(User user) {

        try {
            //Check if row exists, if not return false
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT userId FROM user WHERE userId='"+user.getUserID()+"'");
            if (!result.next())
                return false;

            //Update the User table
            PreparedStatement prepared = connection.prepareStatement
                    ("UPDATE user SET username = ?, password = ?, isLoggedIn = ? WHERE userId = ?");

            prepared.setString(1, user.getUserName());
            prepared.setString(2, user.getPassword());
            prepared.setBoolean(3, user.isLoggedIn());
            prepared.setInt(4, user.getUserID());
            int rows = prepared.executeUpdate();

            if (rows > 0) {
                //Retrieve the FK_userInfoId from the User table
                int userInfoId = -1;
                statement = connection.createStatement();
                result = statement.executeQuery("SELECT FK_userInfoId FROM user WHERE userId='"+user.getUserID()+"'");
                if (result.next()) {
                    userInfoId = result.getInt(1);
                }

                //Update the UserInfo table
                UserInfo userinfo = user.getUserInfo();
                if (userinfo != null) {
                    prepared = connection.prepareStatement
                            ("UPDATE userinfo SET firstName = ?, lastName = ? WHERE userInfoId = ?");

                    prepared.setString(1, userinfo.getFirstName());
                    prepared.setString(2, userinfo.getLastName());
                    prepared.setInt(3, userInfoId);
                    prepared.executeUpdate();
                }
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public User findByUserId(int userId) {
        User user = null;
        int userInfoId = -1;

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM user WHERE userId='"+userId+"'");
            if (result.next()) {
                user = new User();
                user.setUserID(result.getInt(1));
                userInfoId = result.getInt(2);
                user.setUserName(result.getString(3));
                user.setPassword(result.getString(4));
            }

            //Create an empty userinfo row and retrieve the auto incremented row id
            result = statement.executeQuery("SELECT * FROM userinfo WHERE userInfoId='"+userInfoId+"'");
            if (result.next()) {
                UserInfo userinfo = new UserInfo();
                userinfo.setFirstName(result.getString(2));
                userinfo.setLastName(result.getString(3));
                user.setUserInfo(userinfo);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public User get(String username) {
        User user = null;
        int userInfoId = -1;

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM user WHERE username='"+username+"'");
            if (result.next()) {
                user = new User();
                user.setUserID(result.getInt(1));
                userInfoId = result.getInt(2);
                user.setUserName(result.getString(3));
                user.setPassword(result.getString(4));
            }

            //Create an empty userinfo row and retrieve the auto incremented row id
            result = statement.executeQuery("SELECT * FROM userinfo WHERE userInfoId='"+userInfoId+"'");
            if (result.next()) {
                UserInfo userinfo = new UserInfo();
                userinfo.setFirstName(result.getString(2));
                userinfo.setLastName(result.getString(3));
                user.setUserInfo(userinfo);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public boolean delete(String username) {

        try {

            //Retrieve the FK_userInfoId from the User table
            int userId = -1;
            int userInfoId = -1;
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT userId, FK_userInfoId FROM user WHERE username='"+username+"'");
            if (result.next()) {
                userId = result.getInt(1);
                userInfoId = result.getInt(2);
            }
            else
                return false;

            //Delete the row in the user table with userId
            PreparedStatement prepared = connection.prepareStatement
                    ("DELETE FROM user WHERE userId = ?");
            prepared.setInt(1, userId);
            int rows = prepared.executeUpdate();

            if (rows > 0) {
                //Delete the row in the userinfo table with userInfoId
                prepared = connection.prepareStatement
                        ("DELETE FROM userinfo WHERE userInfoId = ?");
                prepared.setInt(1, userInfoId);
                prepared.executeUpdate();
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public boolean isLoggedIn(String username) {
        boolean isLoggedIn = false;

        try {
            if (!username.isEmpty()) {
                //Check if user is logged in
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT isLoggedIn FROM user WHERE username='"+username+"'");
                if (result.next())
                    isLoggedIn = result.getBoolean(1);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return isLoggedIn;
    }

    public boolean setLoginStatus(String username, boolean status) {
        boolean isLoggedIn = false;

        try {
            if (!username.isEmpty()) {
                //Update the User login status
                PreparedStatement prepared = connection.prepareStatement
                        ("UPDATE user SET isLoggedIn = ? WHERE username = ?");

                prepared.setBoolean(1, status);
                prepared.setString(2, username);
                int rows = prepared.executeUpdate();
                isLoggedIn = (rows > 0) ? true : false;
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return isLoggedIn;
    }

    public List<String> getLoggedInUsers() {
        List<String> users = new ArrayList<String>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT isLoggedIn, username FROM user WHERE isLoggedIn='TRUE'");
            if (result.next()) {
                boolean isLoggedIn = result.getBoolean(1);
                if (isLoggedIn)
                    users.add(result.getString(2));
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

}
