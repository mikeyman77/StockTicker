package com.stockticker.logic;

import com.stockticker.UserInfo;

/**
 * This is the interface defines the functionality for user authorization.
 * It provides methods to register, unregister, log in, log out, unregister,
 * change password, etc.
 *
 * @author Michael Grissom
 */
public interface AuthorizationService {
    
    /**
     * This method logs in a specific user to the system.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return true if user was logged in
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public boolean logIn(String username, String password) 
            throws BusinessLogicException;
    
    /**
     * This method logs out a specific user from the system.
     *
     * @param username the username of the user
     * @return true if user was logged out
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public boolean logOut(String username) throws BusinessLogicException;
    
    /**
     * This method returns whether a specific user is logged in to the system
     *
     * @param username the username of the user
     * @return true if user is currently logged in
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public boolean isLoggedIn(String username) throws BusinessLogicException;
    
    /**
     * This method registers a specific user to the system.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param userInfo a UserInfo object containing name
     * @return true if user was registered
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public boolean register(String username, String password, UserInfo userInfo) 
            throws BusinessLogicException;
    
    /**
     * This method un-registers a user from the system.
     *
     * @param username the username of the user
     * @return true if the user was unregistered
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public boolean unRegister(String username) throws BusinessLogicException;
    
    /**
     * This method checks if the user is registered.
     *
     * @param username the username of the user
     * @return true if the user is registered
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public boolean isRegistered(String username) throws BusinessLogicException;
    
    /**
     * This method gets the user's information.
     *
     * @param username the username of the user
     * @return UserInfo object related to the username
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public UserInfo getUserInfo(String username) throws BusinessLogicException;
    
    /**
     * This method updates the user's information.
     *
     * @param username the username of the user
     * @param userInfo a UserInfo object with the user's information
     * @return true if the user's information was changed
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public boolean updateUserInfo(String username, UserInfo userInfo) 
            throws BusinessLogicException;
    
    /**
     * This method changes the password for a user.
     *
     * @param username the username of the user
     * @param oldPassword the user's old password
     * @param newPassword the user's new password
     * @return true if the password was changed
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public boolean changePassword(String username, String oldPassword, 
            String newPassword) throws BusinessLogicException;

}
