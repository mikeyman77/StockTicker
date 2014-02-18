package com.stockticker.logic;

import com.stockticker.UserInfo;

public enum UserAuthorization implements AuthorizationService {
    INSTANCE;

    @Override
    public boolean logIn(String username, String password) {
        return false;
    }

    @Override
    public boolean logOut(String username) {
        return false;
    }

    @Override
    public boolean isLoggedIn(String username) {
        return false;
    }

    @Override
    public boolean register(String username, String password) {
        return false;
    }

    @Override
    public boolean unRegister(String username) {
        return false;
    }

    @Override
    public boolean isRegistered(String username) {
        return false;
    }

    @Override
    public UserInfo getUserInfo(String username) {
        return null;
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        return false;
    }
}
