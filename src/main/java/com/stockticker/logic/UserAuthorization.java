package com.stockticker.logic;

import com.stockticker.User;
import com.stockticker.UserInfo;
import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.PersistenceServiceException;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

/**
 * This class provides user authorization functionality.
 * 
 * @author Michael Grissom
 */
public enum UserAuthorization implements AuthorizationService {

    /**
     * Instance of user authorization service.
     */
    INSTANCE;
    
    private static final Logger logger = 
            LogManager.getLogger(UserAuthorization.class.getName());
    
    private BusinessLogicService bls;
    private PersistenceService persistence;
    private BasicPasswordEncryptor passwordEncryptor;

    private UserAuthorization() {
        PropertyConfigurator.configure("./config/log4j.properties");
    }
    
    // This method is only called by the Business Logic Service
    void start() {
        bls = BusinessLogicService.INSTANCE;
        persistence = bls.getPersistence();
        
        passwordEncryptor = new BasicPasswordEncryptor();
    }
    
    // This method is only called by the Business Logic Service
    void stop() {
        bls = null;
        persistence = null;
        
        passwordEncryptor = null;
    }
    
    /**
     * This method logs in a specific user to the system.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return true if user was logged in
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public boolean logIn(String username, String password) throws BusinessLogicException {
        User user;
        boolean successful = false;
        
        try {
            user = persistence.getUser(username);

            if (user != null) {
                if (checkPassword(password, user.getPassword())) {
                    persistence.setLoginStatus(user.getUserName(), true);
                    successful = true;
                }
            }
        }
        catch (PersistenceServiceException e) {
            logger.error("Unable to log in user", e);
            throw new BusinessLogicException("Error: Unable to log in user, check logs", e);
        }
        
        return successful;
    }
    
    /**
     * This method logs out a specific user from the system.
     *
     * @param username the username of the user
     * @return true if user was logged out
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public boolean logOut(String username) throws BusinessLogicException {
        User user;
        boolean successful = false;
        
        try {
            user = persistence.getUser(username);

            if (user != null && user.isLoggedIn()) {
                successful = persistence.setLoginStatus(username, false);
            }
        }
        catch (PersistenceServiceException e) {
            logger.error("Unable to log out user", e);
            throw new BusinessLogicException("Error: Unable to log out user, check logs", e);
        }
        
        return successful;
    }
    
    /**
     * This method returns whether a specific user is logged in to the system
     *
     * @param username the username of the user
     * @return true if user is currently logged in
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public boolean isLoggedIn(String username) throws BusinessLogicException {
        boolean isLoggedIn = false;
        
        try {
            isLoggedIn = persistence.isLoggedIn(username);
        }
        catch (PersistenceServiceException e) {
            logger.error("Unable to get logged in status for user", e);
            throw new BusinessLogicException("Error: Unable to get the login status for user, check logs", e);
        }
        return isLoggedIn;
    }
    
    /**
     * This method registers a specific user to the system.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param userInfo a UserInfo object containing name
     * @return true if user was registered
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public boolean register(String username, String password, UserInfo userInfo) 
            throws BusinessLogicException {
        
        User user;
        boolean successful = false;
        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        
        try {
            user = persistence.createUser(username, encryptedPassword);

            if (user != null) {
                user.setUserInfo(userInfo);
                successful = persistence.updateUser(user);
            }
        }
        catch (PersistenceServiceException e) {
            logger.error("Unable to register user", e);
            throw new BusinessLogicException("Error: Unable to register user, check logs", e);
        }

        return successful;
    }
    
    /**
     * This method un-registers a user from the system.
     *
     * @param username the username of the user
     * @return true if the user was unregistered
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public boolean unRegister(String username) throws BusinessLogicException {
        boolean successful = false;
        
        try {
            successful = persistence.deleteUser(username);
        }
        catch (PersistenceServiceException e) {
            logger.error("Unable to unregister user", e);
            throw new BusinessLogicException("Error: Unable to unregister user, check logs", e);
        }
        
        return successful;
    }
    
    /**
     * This method checks if the user is registered.
     *
     * @param username the username of the user
     * @return true if the user is registered
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public boolean isRegistered(String username) throws BusinessLogicException {
        boolean successful = false;
        
        try {
            successful = persistence.userExists(username);
        }
        catch (PersistenceServiceException e) {
            logger.error("Unable to get registration status for user", e);
            throw new BusinessLogicException("Error: Unable to registeration status for user, check logs", e);
        }
        
        return successful;
    }
    
    /**
     * This method gets the user's information.
     *
     * @param username the username of the user
     * @return UserInfo object related to the username
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public UserInfo getUserInfo(String username) throws BusinessLogicException {
        UserInfo userInfo;
        
        try {
            userInfo = persistence.getUserInfo(username);
        }
        catch (PersistenceServiceException e) {
            logger.error("Unable to get user information for user", e);
            throw new BusinessLogicException("Error: Unable to get user info for user, check logs", e);
        }
        
        return userInfo;
    }
    
    /**
     * This method changes the password for a user.
     *
     * @param username the username of the user
     * @param oldPassword the user's old password
     * @param newPassword the user's new password
     * @return true if the password was changed
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) 
            throws BusinessLogicException {
        
        User user;
        String encryptedPassword;
        boolean successful = false;
        
        try {
            user = persistence.getUser(username);

            if (user != null && user.isLoggedIn()) {
                if (checkPassword(oldPassword, user.getPassword())) {
                    encryptedPassword = passwordEncryptor.encryptPassword(newPassword);
                    user.setPassword(encryptedPassword);
                    successful = persistence.updateUser(user);
                }
            }
        }
        catch (PersistenceServiceException e) {
            logger.error("Unable to change password for user", e);
            throw new BusinessLogicException("Error: Unable to change password for user, check logs", e);
        }
        
        return successful;
    }
    
    /**
     * This method updates the user's information.
     *
     * @param username the username of the user
     * @param userInfo a UserInfo object with the user's information
     * @return true if the user's information was changed
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public boolean updateUserInfo(String username, UserInfo userInfo) 
            throws BusinessLogicException {
        
        User user;
        boolean successful = false;
        
        try {
            user = persistence.getUser(username);

            if (user != null && user.isLoggedIn()) {
                user.setUserInfo(userInfo);
                successful = persistence.updateUser(user);
            }
        }
        catch (PersistenceServiceException e) {
            logger.error("Unable to update user information for user", e);
            throw new BusinessLogicException("Error: Unable to update user info for user, check logs", e);
        }
        
        return successful;
    }
    
    // helper method to check password for user
    private boolean checkPassword(String password, String encryptedPassword) {
        return passwordEncryptor.checkPassword(password, encryptedPassword);
    }
}
