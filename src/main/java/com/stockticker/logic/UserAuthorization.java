package com.stockticker.logic;

import com.stockticker.User;
import com.stockticker.UserInfo;
import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.StockTickerPersistence;
import java.util.List;
import org.jasypt.util.password.BasicPasswordEncryptor;

public enum UserAuthorization implements AuthorizationService {
    INSTANCE;

    private final PersistenceService persistence = StockTickerPersistence.INSTANCE;
    private final BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

    @Override
    public boolean logIn(String username, String password) {
        User user;
        boolean successful = false;
        
        // check if user exists
        if (persistence.userExists(username)) {
            user = persistence.getUser(username);
        }
        else {
            return successful;
        }
        
        // get all logged in users
        List<String> loggedInUsers = persistence.getLoggedInUsers();
        
        // log out all users but the username
        if (!loggedInUsers.isEmpty()) {
            for (String uname : loggedInUsers) {
                if (!username.equals(uname)) {
                    persistence.setLoginStatus(uname, false);
                }
            }
        }
        
        // check password
        if (checkPassword(password, user.getPassword())) {
            user.setLoggedIn(true);
            persistence.setLoginStatus(user.getUserName(), true);
            successful = true;
        }
        
        return successful;
    }

    @Override
    public boolean logOut(String username) {
        boolean successful = false;
        List<String> loggedInUsers = persistence.getLoggedInUsers();
        
        if (!loggedInUsers.isEmpty()) {
            if (loggedInUsers.contains(username)) {
                successful = persistence.setLoginStatus(username, false);
            }
        }
        
        return successful;
    }

    @Override
    public boolean isLoggedIn(String username) {
        return persistence.isLoggedIn(username);
    }

    @Override
    public boolean register(String username, String password, UserInfo userInfo) {
        boolean successful = false;
        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        
        User user = persistence.createUser(username, encryptedPassword);
        
        if (user != null) {
            user.setUserInfo(userInfo);
            successful = persistence.updateUser(user);
        }

        return successful;
    }

    @Override
    public boolean unRegister(String username) {
        boolean successful = false;
        
        if (persistence.userExists(username)) {
            successful = persistence.deleteUser(username);
        }
        
        return successful;
    }

    @Override
    public boolean isRegistered(String username) {
        return persistence.userExists(username);
    }

    @Override
    public UserInfo getUserInfo(String username) {
        return persistence.getUserInfo(username);
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        boolean successful = false;
        List<String> loggedInUsers = persistence.getLoggedInUsers();
        
        // only allow logged in users to change password
        if (loggedInUsers.contains(username)) {
            User user = persistence.getUser(username);
            
            if (checkPassword(oldPassword, user.getPassword())) {
                user.setPassword(newPassword);
                successful = true;
            }
        }
        
        return successful;
    }
    
    @Override
    public boolean updateUserInfo(String username, UserInfo userInfo) {
        boolean successful = false;
        List<String> loggedInUsers = persistence.getLoggedInUsers();
        
        if (loggedInUsers.isEmpty())
            return successful;
        
        if (loggedInUsers.contains(username)) {
            User user = persistence.getUser(username);
            user.setUserInfo(userInfo);
            successful = persistence.updateUser(user);
        }
        
        return successful;
    }
    
    // helper method to check password for user
    private boolean checkPassword(String password, String encryptedPassword) {
        boolean successful = false;
        
        if (passwordEncryptor.checkPassword(password, encryptedPassword)) {
            successful = true;
        }
        
        return successful;
    }
}
