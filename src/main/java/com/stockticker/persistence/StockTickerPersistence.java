package com.stockticker.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import com.stockticker.Stock;
import com.stockticker.User;

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
                if (tracked != null) {
                    tracked.remove(stock);
                }
            }
        return true;
    }

    @Override
    public boolean isStockTracked(User user, Stock stock) {
        TrackedStocks tracked = trackedStocksMap.get(user.getUserName());

        return tracked.isStockTracked(stock.getSymbol());
    }

    @Override
    public boolean userExists(User user) {
        if (usersMap.containsKey(user.getUserName()))
            return true;
        else
            return false;
    }

    @Override
    public User createUser(User user) {
        User newUser = null;
        if (!usersMap.containsKey(user.getUserName())) {
            userId++;
            newUser = user;
            newUser.setUserID(userId);
            usersMap.put(newUser.getUserName(), newUser);
        }
        else
            newUser = null;

        return newUser;
    }

    @Override
    public boolean updateUser(User user) {
        usersMap.put(user.getUserName(), user);

        return true;
    }

    @Override
    public User loadUser(User user) {
        return usersMap.get(user.getUserName());
    }

    @Override
    public boolean deleteUser(User user) {
        if (usersMap.remove(user.getUserName()) != null)
            return true;
        else
            return false;
    }

    @Override
    public boolean isLoggedIn(User user) {
        if (!usersMap.containsKey(user.getUserName()))
            return false;

        User current = usersMap.get(user.getUserName());
        return current.isLoggedIn();
    }

    @Override
    public boolean setLoginStatus(User user) {
        if (usersMap.containsKey(user.getUserName()))
            usersMap.put(user.getUserName(), user);
        else
            return false;

        return true;
    }

    @Override
    public List<User> getLoggedInUsers() {
        List<User> loggedInUsers = null;

        if (usersMap.size() > 0) {
            User user = null;
            loggedInUsers = new ArrayList<User>();
            Iterator<User> users = usersMap.values().iterator();
            while (users.hasNext()) {
                user = users.next();
                if (user.isLoggedIn()) {
                    loggedInUsers.add(user);
                }
            }
        }

        return loggedInUsers;
    }

    public static void main(String [] args) {
        PersistenceService ps = StockTickerPersistence.INSTANCE;

        User sconnall = new User("sconnall", "redsox");
        sconnall = ps.createUser(sconnall);
        sconnall.setLoggedIn(true);
        ps.setLoginStatus(sconnall);
        System.out.print("User " + sconnall.getUserName() + " is ");
        if (ps.isLoggedIn(sconnall)) {
            System.out.println("logged in.");
        }
        else {
            System.out.println("not logged in.");
        }
    }

}