package com.stockticker.logic;

import com.stockticker.User;
import com.stockticker.UserInfo;
import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.PersistenceServiceException;
import org.jasypt.util.password.BasicPasswordEncryptor;

public enum UserAuthorization implements AuthorizationService {
    INSTANCE;
    
    private BusinessLogicService bls;
    private PersistenceService persistence;
    private BasicPasswordEncryptor passwordEncryptor;
    
    // This method is only called by the Business Logic Service
    void start() {
        bls = BusinessLogicService.INSTANCE;
        persistence = bls.getPersistence();
        
        passwordEncryptor = new BasicPasswordEncryptor();
    }
    
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
            throw new BusinessLogicException("Error: Unable to log in user, check logs", e);
        }
        
        return successful;
    }
    
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
            throw new BusinessLogicException("Error: Unable to log out user, check logs", e);
        }
        
        return successful;
    }
    
    @Override
    public boolean isLoggedIn(String username) throws BusinessLogicException {
        boolean isLoggedIn = false;
        
        try {
            isLoggedIn = persistence.isLoggedIn(username);
        }
        catch (PersistenceServiceException e) {
            throw new BusinessLogicException("Error: Unable to get the login status for user, check logs", e);
        }
        return isLoggedIn;
    }
    
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
            throw new BusinessLogicException("Error: Unable to register user, check logs", e);
        }

        return successful;
    }
    
    @Override
    public boolean unRegister(String username) throws BusinessLogicException {
        boolean successful = false;
        
        try {
            successful = persistence.deleteUser(username);
        }
        catch (PersistenceServiceException e) {
            throw new BusinessLogicException("Error: Unable to unregister user, check logs", e);
        }
        
        return successful;
    }
    
    @Override
    public boolean isRegistered(String username) throws BusinessLogicException {
        boolean successful = false;
        
        try {
            successful = persistence.userExists(username);
        }
        catch (PersistenceServiceException e) {
            throw new BusinessLogicException("Error: Unable to registeration status for user, check logs", e);
        }
        
        return successful;
    }
    
    @Override
    public UserInfo getUserInfo(String username) throws BusinessLogicException {
        UserInfo userInfo;
        
        try {
            userInfo = persistence.getUserInfo(username);
        }
        catch (PersistenceServiceException e) {
            throw new BusinessLogicException("Error: Unable to get user info for user, check logs", e);
        }
        
        return userInfo;
    }
    
    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) 
            throws BusinessLogicException {
        
        User user;
        boolean successful = false;
        
        try {
            user = persistence.getUser(username);

            if (user != null && user.isLoggedIn()) {
                if (checkPassword(oldPassword, user.getPassword())) {
                    user.setPassword(newPassword);
                    successful = persistence.updateUser(user);
                }
            }
        }
        catch (PersistenceServiceException e) {
            throw new BusinessLogicException("Error: Unable to change password for user, check logs", e);
        }
        
        return successful;
    }
    
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
            throw new BusinessLogicException("Error: Unable to update user info for user, check logs", e);
        }
        
        return successful;
    }
    
    // helper method to check password for user
    private boolean checkPassword(String password, String encryptedPassword) {
        return passwordEncryptor.checkPassword(password, encryptedPassword);
    }
}
