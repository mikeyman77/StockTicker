package com.stockticker.logic;

import com.stockticker.User;

public enum UserAuthorization implements AuthorizationService {
    INSTANCE;

    @Override
    public boolean logIn(User user) {
        return false;
    }

    @Override
    public boolean logOut(User user) {
        return false;
    }

    @Override
    public boolean register(User user) {
        return false;
    }

    @Override
    public boolean unRegister(User user) {
        return false;
    }

    @Override
    public boolean isLoggedIn(User user) {
        return false;
    }

}
