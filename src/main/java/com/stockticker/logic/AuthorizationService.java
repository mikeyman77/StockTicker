package com.stockticker.logic;

import com.stockticker.User;

public interface AuthorizationService {

    boolean logIn(String username, String password);
    boolean logOut(String username);
    boolean isLoggedIn(User user);
    boolean register(User user);
    boolean unRegister(String username);
    boolean isRegistered(String username);
    User createUser(String username, String password);
    User getUser(String username);

}
