package com.stockticker.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import com.stockticker.Stock;
import com.stockticker.User;
import com.stockticker.UserInfo;

public enum StockTickerPersistence implements PersistenceService {
    INSTANCE;


    private Map<String,Stock> stocksMap = new TreeMap<String,Stock>();
    private Map<String,TrackedStocks> trackedStocksMap = new TreeMap<String,TrackedStocks>();
    private Map<String,User> usersMap = new TreeMap<String, User>();
    private int userId = 0;

    @Override
    public List<Stock> getTrackedStocks(User user) {
        TrackedStocks tracked = trackedStocksMap.get(user.getUserName());
        return new ArrayList<Stock>(tracked.getStocks());
    }

    @Override
    public boolean trackStock(User user, Stock stock, boolean track) {
            if (user == null || stock == null)
                return false;

            TrackedStocks tracked = trackedStocksMap.get(user.getUserName());
            if (track) {
                if (tracked != null) {
                    tracked.put(stock);
                }
                else {
                    tracked = new TrackedStocks(user.getUserName());
                    trackedStocksMap.put(user.getUserName(), tracked);
                }
            }
            else {
                tracked.remove(stock);
            }
        return true;
    }

    @Override
    public boolean isStockTracked(User user, Stock stock) {
        if (user == null || stock == null)
            return false;

        TrackedStocks tracked = trackedStocksMap.get(user.getUserName());

        return tracked.isStockTracked(stock.getSymbol());
    }

    @Override
    public boolean userExists(String username) {
        if (! usersMap.containsKey(username))
            return false;

        return true;
    }

    @Override
    public User createUser(String username, String password) {

        //if user is null or already exists, return null
        if (usersMap.containsKey(username))
            return null;

        //otherwise, create the new user
        userId++;
        User user = new User(username, password);
        user.setUserID(userId);
        usersMap.put(user.getUserName(), user);

        return user;
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null || !usersMap.containsKey(user.getUserName()))
            return false;

        usersMap.put(user.getUserName(), user);
        return true;
    }

    @Override
    public User getUser(String username) {
        return usersMap.get(username);
    }

    @Override
    public boolean deleteUser(String username) {
        if (usersMap.remove(username) == null)
            return false;

        return true;
    }

    @Override
    public boolean isLoggedIn(String username) {
        if (!usersMap.containsKey(username))
            return false;

        User current = usersMap.get(username);
        return current.isLoggedIn();
    }

    @Override
    public boolean setLoginStatus(String username, boolean status) {
        if (!usersMap.containsKey(username))
            return false;

        User cachedUser = usersMap.remove(username);
        cachedUser.setLoggedIn(status);
        usersMap.put(username, cachedUser);
        return true;
    }

    @Override
    public List<String> getLoggedInUsers() {
        List<String> loggedInUsers = new ArrayList<String>();

        if (usersMap.size() > 0) {
            User user = null;
            Iterator<User> users = usersMap.values().iterator();
            while (users.hasNext()) {
                user = users.next();
                if (user.isLoggedIn()) {
                    loggedInUsers.add(user.getUserName());
                }
            }
        }

        return loggedInUsers;
    }

    @Override
    public UserInfo getUserInfo(String username) {
        if (!usersMap.containsKey(username))
            return null;

        User current = usersMap.get(username);
        return current.getUserInfo();
    }

/*
    public static void main(String [] args) {
        PersistenceService ps = StockTickerPersistence.INSTANCE;

        User sconnall = new User("sconnall", "redsox");
        sconnall = ps.createUser(sconnall);

        //Set logged in and test
        ps.setLoginStatus(sconnall.getUserName(), true);
        System.out.print("User " + sconnall.getUserName() + " is ");
        if (ps.isLoggedIn(sconnall.getUserName())) {
            System.out.println("logged in.");
        }
        else {
            System.out.println("not logged out.");
        }

        //set logged out and test
        ps.setLoginStatus(sconnall.getUserName(), false);
        System.out.print("User " + sconnall.getUserName() + " is ");
        if (ps.isLoggedIn(sconnall.getUserName())) {
            System.out.println("logged in.");
        }
        else {
            System.out.println("logged out.");
        }

        //Set logged in and test
        ps.setLoginStatus(sconnall.getUserName(), true);
        System.out.print("User " + sconnall.getUserName() + " is ");
        if (ps.isLoggedIn(sconnall.getUserName())) {
            System.out.println("logged in.");
        }
        else {
            System.out.println("logged out.");
        }

        //Create new user and log in
        User sconnall2 = new User("sconnall2", "redsox");
        ps.setLoginStatus(sconnall2.getUserName(), true);
        System.out.print("User " + sconnall2.getUserName() + " is ");
        if (ps.isLoggedIn(sconnall2.getUserName())) {
            System.out.println("logged in.");
        }
        else {
            System.out.println("logged out.");
        }

        User user = null;
        List<String> loggedInUsers = ps.getLoggedInUsers();
        for (String username : loggedInUsers) {
            System.out.print("User " + username + " is logged in");
        }
    }
*/
}