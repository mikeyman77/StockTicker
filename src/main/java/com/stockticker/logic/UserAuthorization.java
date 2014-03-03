package com.stockticker.logic;

import com.stockticker.User;
import com.stockticker.UserInfo;
import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.StockTickerPersistence;
import java.util.List;

public enum UserAuthorization implements AuthorizationService {
    INSTANCE;

    private PersistenceService persistence = StockTickerPersistence.INSTANCE;

    @Override
    public boolean logIn(String username, String password) {
        User tempUser = new User(username, password);
        User user = null;
        boolean successful = false;
        
        // check if user exists
        if (persistence.userExists(tempUser))
            user = persistence.loadUser(tempUser);
        
        else
            return successful;
        
        // get all logged in users
        List<String> loggedInUsers = persistence.getLoggedInUsers();
        
        // log out all users but the username
        if (loggedInUsers != null && !loggedInUsers.isEmpty()) {
            for (String uname : loggedInUsers) {
                if (uname != username)
                    persistence.setLoginStatus(uname, false);
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
        
        if (loggedInUsers != null && !loggedInUsers.isEmpty()) {
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
    public boolean register(String username, String password) {
        
        if (persistence.createUser(new User(username, password)) != null) {
            return true;
        }

        return false;
    }

    @Override
    public boolean unRegister(String username) {
        User user = new User(username, "");
        
        if (persistence.userExists(user)) {
            return persistence.deleteUser(user);
        }
        
        return false;
    }

    @Override
    public boolean isRegistered(String username) {
        return persistence.userExists(new User(username, ""));
    }

    @Override
    public UserInfo getUserInfo(String username) {
        return null;
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        boolean successful = false;
        List<String> loggedInUsers = persistence.getLoggedInUsers();
        
        // only allow logged in users to change password
        if (loggedInUsers.contains(username)) {
            User user = persistence.loadUser(new User(username, ""));
            
            if (checkPassword(oldPassword, user.getPassword())) {
                user.setPassword(newPassword);
                successful = true;
            }
        }
        
        return successful;
    }
    
    // helper method to check password for user
    private boolean checkPassword(String password, String userPassword) {
        if (password == userPassword)
            return true;
        else
            return false;
    }
}
