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

    public boolean logIn(String username, String password) 
            throws BusinessLogicException;
    public boolean logOut(String username) throws BusinessLogicException;
    public boolean isLoggedIn(String username) throws BusinessLogicException;
    public boolean register(String username, String password, UserInfo userInfo) 
            throws BusinessLogicException;
    public boolean unRegister(String username) throws BusinessLogicException;
    public boolean isRegistered(String username) throws BusinessLogicException;
    public UserInfo getUserInfo(String username) throws BusinessLogicException;
    public boolean updateUserInfo(String username, UserInfo userInfo) 
            throws BusinessLogicException;
    public boolean changePassword(String username, String oldPassword, 
            String newPassword) throws BusinessLogicException;

}
