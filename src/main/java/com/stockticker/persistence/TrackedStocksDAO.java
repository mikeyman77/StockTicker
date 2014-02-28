package com.stockticker.persistence;

import com.stockticker.Stock;
import com.stockticker.User;
import java.util.List;

/**
 * Interface that defines the Data Access methods for the
 * tracking of stocks that users are interested in.
 *
 * @author Stuart Connall
 * @version 1.0 02/27/2014
 */
public interface TrackedStocksDAO {

    /**
     * Gets the stockId associated with the specified symbol
     *
     * @param symbol the name of the stock
     * @return the stock id, -1 otherwise
     */
    public int     getStockId(String symbol);

    /**
     * Adds a row to the tracked stocks table with userId and stockId
     *
     * @param userId  the id associated with the user row
     * @param stockId the id associated with the stock row
     * @return true if added, false otherwise
     */
    public boolean add(int userId, int stockId);

    /**
     * Tests if the row matching the userId and stockId exists in the
     * tracked stocks table
     *
     * @param userId  the id associated with the user row
     * @param stockId the id associated with the stock row
     * @return true if exists, false otherwise
     */
    public boolean exists(int userId, int stockId);

    /**
     * Retrieves all tracked stocks associated with the userId
     *
     * @param  userId the id associated with the user row
     * @return a list of stocks or an empty list
     */
    public List<String> get(int userId);

    /**
     * Deletes the row from the tracked stocks table associated
     * with the userId and stockId
     *
     * @param userId  the id associated with the user row
     * @param stockId the id associated with the stock row
     * @return true if successful, false otherwise
     */
    public boolean delete(int userId, int stockId);

    /**
     * Deletes all tracked stocks associated with the userId
     *
     * @param  userId the id associated with the user
     * @return true if successful, false otherwise
     */
    public boolean deleteAll(int userId);
}
