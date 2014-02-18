package com.stockticker.logic;

import com.stockticker.User;

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
        public boolean isLoggedIn(User user) {
            return false;
        }

        @Override
        public boolean register(User user) {
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
        public User createUser(String username, String password) {
            return null;
        }

        @Override
        public User getUser(String username) {
            return null;
        }

}
