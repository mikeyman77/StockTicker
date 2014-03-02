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
import java.util.Date;
import java.util.List;

/**
 * Data Access methods for User related data
 *
 * @author Stuart Connall
 * @version 1.0 2/17/14.
 */
public class UserDAOImpl implements UserDAO {

    private Connection connection;

    /**
     * Constructs the UserDAO implementation and stores a JDBC connection
     *
     * @param conn A JDBC connection
     */
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
        int userId = -1;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT userId FROM user WHERE username='"+username+"'");
            if (result.next())
                userId = result.getInt(1);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return userId;
    }

    /**
     * Create a row in the user table and stores the username and
     * password.
     *
     * @param   username the name of the user
     * @param   password the user's password
     * @return  a new User with id, username, and password, null if creation failed
     */
    public User create(String username, String password){
        boolean userCreated = false;
        User user = null;

            //check if the user already exists
            if (!exists(username)) {
                try {
                    //Create an empty userinfo row and retrieve the auto incremented row id
                    //Statement statement = connection.createStatement();
                    //statement.executeUpdate("INSERT INTO userinfo (firstName) VALUES ('')");
                    //ResultSet result = statement.executeQuery("CALL IDENTITY();");
                    int userInfoId = -1;
                    //if (result.next())
                    //    id = result.getInt(1);

                    //Create an empty userinfo row and retrieve the auto increment row id
                    PreparedStatement prepared = connection.prepareStatement("INSERT INTO userinfo (firstName) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                    prepared.setString(1, "");
                    prepared.execute();
                    ResultSet result = prepared.getGeneratedKeys();
                    if (result.next() ) {
                        userInfoId = result.getInt(1);
                    }

                    //insert the user row with username, password, and id from userinfo table insert
                    int userId = -1;
                    prepared = connection.prepareStatement("INSERT INTO user (FK_userInfoId, username, password, joinedDate, isLoggedIn) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    prepared.setInt(1, userInfoId);
                    prepared.setString(2, username);
                    prepared.setString(3, password);
                    prepared.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    prepared.setBoolean(5, false);
                    prepared.execute();
                    result = prepared.getGeneratedKeys();
                    if (result.next() ) {
                        userId = result.getInt(1);
                    }

                    //create and return new User object
                    user = new User(username, password);
                    user.setUserID(userId);
                    user.setUserInfo(new UserInfo("", ""));
                }
                catch(SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

        return user;
    }

    /**
     * Checks if the user exists
     *
     * @param username  the name of the user
     * @return  true if exists, false otherwise
     */
    public boolean exists(String username) {
        boolean userExists = false;

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT username FROM user WHERE username='"+username+"'");
            if (result.next()) {
                userExists = true;
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return userExists;
    }

    /**
     * Updates the user data
     *
     * @param user  the User object to persist
     * @return  true if updated, false otherwise
     */
    public boolean update(User user) {
        boolean updateSuccessful = false;

        //Check if row exists, if not return false
            if (user != null && exists(user.getUserName())) {
                //Update the User table
                try {
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
                        Statement statement = connection.createStatement();
                        ResultSet result = statement.executeQuery("SELECT FK_userInfoId FROM user WHERE userId='"+user.getUserID()+"'");
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

                        updateSuccessful = true;
                    }
                }
                catch(SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

        return updateSuccessful;
    }

    /**
     * Finds a user by row id
     *
     * @param userId  the user's row id
     * @return  returns a User object, null if no user
     */
/*
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
*/

    /**
     * Gets the user row associated with the user name
     *
     * @param username the name of the user
     * @return  a User object or null if it doesn't exist
     */
    public User get(String username) {
        User user = null;
        int userInfoId = -1;

        try {
            //Retrieve the user row
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM user WHERE username='"+username+"'");
            if (result.next()) {
                user = new User();
                user.setUserID(result.getInt(1));
                userInfoId = result.getInt(2);
                user.setUserName(result.getString(3));
                user.setPassword(result.getString(4));
            }

            //Retrieve the userinfo row
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

    /**
     * Deletes the user row associated with the username
     *
     * @param username  the name of the user
     * @return  true if delete successful, false otherwise
     */
    public boolean delete(String username) {
        boolean deleteSuccessful = false;

        if (exists(username)) {
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
                    deleteSuccessful = true;
                } else {
                    deleteSuccessful = false;
                }
            }
            catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return deleteSuccessful;
    }

    /**
     * Checks if the user is currently logged in
     *
     * @param username  the name of the user
     * @return  true if logged in, false otherwise
     */
    public boolean isLoggedIn(String username) {
        boolean isLoggedIn = false;

        try {
            if (!username.isEmpty()) {
                //Check if user is logged in
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT isLoggedIn FROM user WHERE username='"+username+"'");
                if (result.next()) {
                    isLoggedIn = result.getBoolean(1);
                }
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return isLoggedIn;
    }

    /**
     * Set a user's logged in status to true or false
     *
     * @param username  the name of the user
     * @param status    login status, true or false
     * @return  true if status set, false otherwise
     */
    public boolean setLoginStatus(String username, boolean status) {
        boolean statusSet = false;

        try {
            if (!username.isEmpty()) {
                //Update the User login status
                PreparedStatement prepared = connection.prepareStatement
                        ("UPDATE user SET isLoggedIn = ? WHERE username = ?");

                prepared.setBoolean(1, status);
                prepared.setString(2, username);
                int rows = prepared.executeUpdate();
                statusSet = (rows > 0) ? true : false;
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return statusSet;
    }

    /**
     * Returns a list of logged in users.
     *
     * @return  list of logged in users
     */
    public List<String> getLoggedInUsers() {
        List<String> users = new ArrayList<String>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT isLoggedIn, username FROM user WHERE isLoggedIn='TRUE'");
            if (result.next()) {
                boolean isLoggedIn = result.getBoolean(1);
                if (isLoggedIn) {
                    users.add(result.getString(2));
                }
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

}
