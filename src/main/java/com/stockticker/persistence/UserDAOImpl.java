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
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

/**
 * Data Access methods for User related data
 *
 * @author Stuart Connall
 * @version 1.0 2/17/14.
 */
public class UserDAOImpl implements UserDAO {

    private final Connection connection;
    static final Logger logger = LogManager.getLogger(UserDAOImpl.class.getName());

    /**
     * Constructs the UserDAO implementation
     */
    public UserDAOImpl() throws PersistenceServiceException {
        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        if (!persistenceConnection.connectionEstablished()) {
            persistenceConnection.start();
        }
        this.connection = persistenceConnection.getConnection();

        //configure log4j
        PropertyConfigurator.configure("./config/log4j.properties");
        logger.info("The user persistence component is ready for service.");
    }

    /**
     * Gets the userId associated with the specified user name
     *
     * @param username the name of the user
     * @return the user id, -1 otherwise
     * @throws PersistenceServiceException
     */
    public int getUserId(String username) throws PersistenceServiceException {
        int userId = -1;
        try {
            PreparedStatement prepared = connection.prepareStatement("SELECT userId FROM user WHERE username=?");
            prepared.setString(1, username);
            ResultSet result = prepared.executeQuery();
            if (result.next())
                userId = result.getInt(1);
        }
        catch (SQLException e) {
            int errorCode = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED;
            String message = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE;
            logger.error(message, e);
            throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
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
     * @throws  PersistenceServiceException
     */
    public User create(String username, String password) throws PersistenceServiceException {
        User user = null;

        //check if the user already exists
        if (!exists(username)) {
            try {

                //insert the user row with username, password, and id from userinfo table insert
                int userId = -1;
                PreparedStatement prepared = connection.prepareStatement("INSERT INTO user (username, password, " +
                                                         "joinedDate, isLoggedIn) VALUES (?,?,?,?)",
                                                       Statement.RETURN_GENERATED_KEYS);
                prepared.setString(1, username);
                prepared.setString(2, password);
                prepared.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                prepared.setBoolean(4, false);
                prepared.execute();
                ResultSet result = prepared.getGeneratedKeys();
                if (result.next() ) {
                    userId = result.getInt(1);
                }

                //Create an empty userinfo row and retrieve the auto increment row id
                prepared = connection.prepareStatement("INSERT INTO userinfo (FK_userId) VALUES (?)",
                        Statement.RETURN_GENERATED_KEYS);
                prepared.setInt(1, userId);
                prepared.execute();

                //create and return new User object
                user = new User(username, password);
                user.setUserID(userId);
                user.setUserInfo(new UserInfo("", ""));
            }
            catch (SQLException e) {
                int errorCode = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED;
                String message = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE;
                logger.error(message, e);
                throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
            }
        }

        return user;
    }

    /**
     * Checks if the user exists
     *
     * @param username  the name of the user
     * @return  true if exists, false otherwise
     * @throws  PersistenceServiceException
     */
    public boolean exists(String username) throws PersistenceServiceException {
        boolean userExists = false;

            int userId = getUserId(username);
            if (userId > 0) {
                userExists = true;
            }

        return userExists;
    }

    /**
     * Updates the user data
     *
     * @param user  the User object to persist
     * @return  true if updated, false otherwise
     * @throws  PersistenceServiceException
     */
    public boolean update(User user) throws PersistenceServiceException {
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

                        //Update the UserInfo table
                        UserInfo userinfo = user.getUserInfo();
                        if (userinfo != null) {

                            prepared = connection.prepareStatement
                                    ("UPDATE userinfo SET firstName = ?, lastName = ? WHERE FK_userId = ?");

                            prepared.setString(1, userinfo.getFirstName());
                            prepared.setString(2, userinfo.getLastName());
                            prepared.setInt(3, user.getUserID());
                            prepared.executeUpdate();
                        }

                        updateSuccessful = true;
                    }
                }
                catch (SQLException e) {
                    int errorCode = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED;
                    String message = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE;
                    logger.error(message, e);
                    throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
                }
            }

        return updateSuccessful;
    }

    /**
     * Gets the user row associated with the user name
     *
     * @param username the name of the user
     * @return  a User object or null if it doesn't exist
     * @throws PersistenceServiceException
     */
    public User get(String username) throws PersistenceServiceException {
        User user = null;

        if (exists(username)) {
            try {
                int userId = getUserId(username);
                //Retrieve the user row
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM user WHERE userId="+userId);
                if (result.next()) {
                    user = new User();
                    user.setUserID(result.getInt(1));
                    user.setUserName(result.getString(2));
                    user.setPassword(result.getString(3));
                    user.setLoggedIn(result.getBoolean(5));
                }

                //Retrieve the userinfo row
                result = statement.executeQuery("SELECT * FROM userinfo WHERE FK_userId="+userId);
                if (result.next()) {
                    UserInfo userinfo = new UserInfo();
                    userinfo.setFirstName(result.getString(3));
                    userinfo.setLastName(result.getString(4));
                    assert user != null;
                    user.setUserInfo(userinfo);
                }
            }
            catch (SQLException e) {
                int errorCode = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED;
                String message = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE;
                logger.error(message, e);
                throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
            }
        }

        return user;
    }

    /**
     * Deletes the user row associated with the username and
     * rows in both child tables, userinfo and tracked_stocks
     * with associated userId values.
     *
     * @param username  the name of the user
     * @return  true if delete successful, false otherwise
     * @throws  PersistenceServiceException
     */
    public boolean delete(String username) throws PersistenceServiceException {
        boolean deleteSuccessful = false;

        if (exists(username)) {
            try {
                int userId = getUserId(username);

                //Delete the row in the user table with userId
                PreparedStatement prepared = connection.prepareStatement
                        ("DELETE FROM user WHERE userId = ?");
                prepared.setInt(1, userId);
                prepared.executeUpdate();
                deleteSuccessful = true;
            }
            catch (SQLException e) {
                int errorCode = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED;
                String message = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE;
                logger.error(message, e);
                throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
            }
        }

        return deleteSuccessful;
    }

    /**
     * Checks if the user is currently logged in
     *
     * @param username  the name of the user
     * @return  true if logged in, false otherwise
     * @throws  PersistenceServiceException
     */
    public boolean isLoggedIn(String username) throws PersistenceServiceException {
        boolean isLoggedIn = false;

        try {
            int userId = getUserId(username);
            if (userId > 0) {
                //Check if user is logged in
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT isLoggedIn FROM user WHERE userId="+userId);
                if (result.next()) {
                    isLoggedIn = result.getBoolean(1);
                }
            }
        }
        catch (SQLException e) {
            int errorCode = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED;
            String message = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE;
            logger.error(message, e);
            throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
        }

        return isLoggedIn;
    }

    /**
     * Set a user's logged in status to true or false
     *
     * @param username  the name of the user
     * @param status    login status, true or false
     * @return  true if status set, false otherwise
     * @throws  PersistenceServiceException
     */
    public boolean setLoginStatus(String username, boolean status) throws PersistenceServiceException {
        boolean statusSet = false;

        try {
            int userId = getUserId(username);
            if (userId > 0) {
                //Update the User login status
                PreparedStatement prepared = connection.prepareStatement
                        ("UPDATE user SET isLoggedIn = ? WHERE userId = ?");

                prepared.setBoolean(1, status);
                prepared.setInt(2, userId);
                int rows = prepared.executeUpdate();
                statusSet = (rows > 0);
            }
        }
        catch (SQLException e) {
            int errorCode = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED;
            String message = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE;
            logger.error(message, e);
            throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
        }

        return statusSet;
    }

    /**
     * Returns a list of logged in users.
     *
     * @return  list of logged in users
     * @throws  PersistenceServiceException
     */
    public List<String> getLoggedInUsers() throws PersistenceServiceException {
        List<String> users = new ArrayList<>();

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
        catch (SQLException e) {
            int errorCode = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED;
            String message = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE;
            logger.error(message, e);
            throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
        }

        return users;
    }

}
