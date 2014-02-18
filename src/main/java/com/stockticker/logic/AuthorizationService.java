package com.stockticker.logic;

import com.stockticker.UserInfo;

public interface AuthorizationService {

    public boolean logIn(String username, String password);
    public boolean logOut(String username);
    public boolean isLoggedIn(String username);
    public boolean register(String username, String password);
    public boolean unRegister(String username);
    public boolean isRegistered(String username);
    public UserInfo getUserInfo(String username);
    public boolean changePassword(String username, String newPassword);

}
