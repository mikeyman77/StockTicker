package com.stockticker.persistence;

import com.stockticker.User;

import java.util.List;

/**
 * <p>Provides the interface for User related database actions</p>
 *
 * @author -  Stuart Connall
 * @see UserDAOImpl
 * @see PersistenceServiceException
 * @version - 1.0 02/25/2014
 */
public interface UserDAO {

    /**
     * Gets the userId associated with the specified user name
     *
     * @param username the name of the user
     * @return the user id, -1 otherwise
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public int getUserId(String username) throws PersistenceServiceException;

    /**
     * Create a row in the user table and stores the username and
     * password.
     *
     * @param   username the name of the user
     * @param   password the user's password
     * @return  a new User with id, username, and password, null if creation failed
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public User create(String username, String password) throws PersistenceServiceException;

    /**
     * Checks if the user exists
     *
     * @param username  the name of the user
     * @return  true if exists, false otherwise
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public boolean exists(String username) throws PersistenceServiceException;

    /**
     * Updates the user data
     *
     * @param user  the User object to persist
     * @return  true if updated, false otherwise
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public boolean update(User user) throws PersistenceServiceException;

    /**
     * Finds a user by row id
     *
     * @param userId  the user's row id
     * @return  returns a User object, null if no user
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    //public User findByUserId(int userId) throws PersistenceServiceException;

    /**
     * Gets the user row associated with the user name
     *
     * @param username the name of the user
     * @return  a User object or null if it doesn't exist
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public User get(String username) throws PersistenceServiceException;

    /**
     * Deletes the user row associated with the username
     *
     * @param username  the name of the user
     * @return  true if delete successful, false otherwise
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public boolean delete(String username) throws PersistenceServiceException;

    /**
     * Checks if the user is currently logged in
     *
     * @param username  the name of the user
     * @return  true if logged in, false otherwise
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public boolean isLoggedIn(String username) throws PersistenceServiceException;

    /**
     * Set a user's logged in status to true or false
     *
     * @param username  the name of the user
     * @param status    login status, true or false
     * @return  true if status set, false otherwise
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public boolean setLoginStatus(String username, boolean status) throws PersistenceServiceException;

    /**
     * Returns a list of logged in users.
     *
     * @return  list of logged in users
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public List<String> getLoggedInUsers() throws PersistenceServiceException;

}
