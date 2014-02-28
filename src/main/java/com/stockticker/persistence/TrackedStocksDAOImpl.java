package com.stockticker.persistence;

import com.stockticker.SymbolMap;

import com.stockticker.Stock;
import com.stockticker.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access methods for the tracking of stocks that users
 * are interested in.
 *
 * @author Stuart Connall
 * @version 1.0 02/27/2014
 */
public class TrackedStocksDAOImpl implements TrackedStocksDAO {

    private Connection connection;
    private UserDAO userDAO;

    public TrackedStocksDAOImpl(Connection conn) {
        this.connection = conn;
        userDAO = new UserDAOImpl(connection);
    }

    /**
     * Gets the stockId associated with the specified symbol
     *
     * @param symbol the name of the stock
     * @return the stock id, -1 otherwise
     */
    public int getStockId(String symbol) {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT stockId FROM stock WHERE symbol='"+symbol+"'");
            if (result.next())
                return result.getInt(1);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    @Override
    public boolean add(int userId, int stockId) {
        try {
            //if trackId returned, then stock is already being tracked
            PreparedStatement prepared = connection.prepareStatement("INSERT INTO tracked_stock (userId, stockId) VALUES (?,?)");
            prepared.setInt(1, userId);
            prepared.setInt(2, stockId);
            prepared.execute();
            //trackId = prepared.getGeneratedKeys();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean exists(int userId, int stockId) {
        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT trackId FROM tracked_stock WHERE userId='"+
                                                       userId+"' AND stockId='"+stockId+"'");
            if (result.next())
                return true;
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public List<String> get(int userId) {
        List<String> trackedStocks = new ArrayList<String>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT symbol FROM stock, tracked_stock "+
                    "WHERE stock.stockId=tracked_stock.stockId and tracked_stock.UserId='"+userId+"'");
            while (result.next()) {
                trackedStocks.add(result.getString(1));
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return trackedStocks;
    }

    @Override
    public boolean delete(int userId, int stockId) {
        try {
            //Update the User table
            PreparedStatement prepared = connection.prepareStatement
                    ("DELETE FROM tracked_stock WHERE userId = ? AND stockId = ?");

            prepared.setInt(1, userId);
            prepared.setInt(2, stockId);
            prepared.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteAll(int userId) {
        try {
            //Update the User table
            PreparedStatement prepared = connection.prepareStatement
                    ("DELETE FROM tracked_stock WHERE userId = ?");

            prepared.setInt(1, userId);
            prepared.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

}
