package com.stockticker.persistence;

import com.stockticker.SymbolMap;

import com.stockticker.Stock;
import com.stockticker.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public TrackedStocksDAOImpl(Connection conn) {
        this.connection = conn;
    }

    @Override
    public boolean create(String username, String stock) {

        PreparedStatement prepared = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT userId FROM user WHERE username='"+username+"'");
            if (!result.next() || !SymbolMap.isValidSymbol(stock))
                return false;

            int userId = result.getInt(1);

            //See if a stock entry exists in the DB, meaning it is or was being tracked by someone
            int stockId = -1;
            statement.executeUpdate("SELECT stockId from stock WHERE symbol='"+stock+"'");
            if (result.next()) {
                stockId = result.getInt(1);
            }
            else {
                prepared = connection.prepareStatement("INSERT INTO stock (symbol) VALUES ('?')");
                prepared.setString(1, stock);
                prepared.execute();
                //stockId = prepared.getGeneratedKeys();
            }

            //check if the stock is already being tracked
            int trackId = -1;
            result = statement.executeQuery("SELECT trackId FROM tracked_stock where userId='" +
                    userId + "' AND stockId='" + stockId + "'");
            if (!result.next()) {
                //if trackId returned, then stock is already being tracked
                prepared = connection.prepareStatement("INSERT INTO tracked_stock (userId, stockId) VALUES ('?','?')");
                prepared.setInt(1, userId);
                prepared.setInt(2, stockId);
                prepared.execute();
                //trackId = prepared.getGeneratedKeys();
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean exists(String username, String stock) {
        return false;
    }

    @Override
    public boolean update(String username, String stock) {
        return false;
    }


    @Override
    public List<String> get(String username) {
        return null;
    }

    @Override
    public boolean delete(String username, String stock) {
        return false;
    }
}
