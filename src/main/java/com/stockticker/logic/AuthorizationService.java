package com.stockticker.logic;

import com.stockticker.User;

public interface AuthorizationService {

    boolean logIn(User user);
    boolean logOut(User user);
    boolean register(User user);
    boolean unRegister(User user);
    boolean isLoggedIn(User user);

}
