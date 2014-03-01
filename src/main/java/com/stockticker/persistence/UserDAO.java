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

    /**
     * Gets the userId associated with the specified user name
     *
     * @param username the name of the user
     * @return the user id, -1 otherwise
     */
    public int getUserId(String username);

    /**
     * Create a row in the user table and stores the username and
     * password.
     *
     * @param   username the name of the user
     * @param   password the user's password
     * @return  a new User with id, username, and password, null if creation failed
     */
    public User create(String username, String password);

    /**
     * Checks if the user exists
     *
     * @param username  the name of the user
     * @return  true if exists, false otherwise
     */
    public boolean exists(String username);

    /**
     * Updates the user data
     *
     * @param user  the User object to persist
     * @return  true if updated, false otherwise
     */
    public boolean update(User user);

    /**
     * Finds a user by row id
     *
     * @param userId  the user's row id
     * @return  returns a User object, null if no user
     */
    //public User findByUserId(int userId);

    /**
     * Gets the user row associated with the user name
     *
     * @param username the name of the user
     * @return  a User object or null if it doesn't exist
     */
    public User    get(String username);

    /**
     * Deletes the user row associated with the username
     *
     * @param username  the name of the user
     * @return  true if delete successful, false otherwise
     */
    public boolean delete(String username);

    /**
     * Checks if the user is currently logged in
     *
     * @param username  the name of the user
     * @return  true if logged in, false otherwise
     */
    public boolean isLoggedIn(String username);

    /**
     * Set a user's logged in status to true or false
     *
     * @param username  the name of the user
     * @param status    login status, true or false
     * @return  true if status set, false otherwise
     */
    public boolean setLoginStatus(String username, boolean status);

    /**
     * Returns a list of logged in users.
     *
     * @return  list of logged in users
     */
    public List<String> getLoggedInUsers();

}
