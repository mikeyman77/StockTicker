package com.stockticker.persistence;

import java.util.List;

/**
 * <p>Interface that defines the Data Access methods that
 * enables users to track stocks of interest.</p>
 *
 * @author Stuart Connall
 * @see TrackedStocksDAOImpl
 * @see PersistenceServiceException
 * @version 1.0 02/27/2014
 */
public interface TrackedStocksDAO {

    /**
     * Gets the stockId associated with the specified symbol
     *
     * @param symbol the name of the stock
     * @return the stock id, -1 otherwise
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public int     getStockId(String symbol) throws PersistenceServiceException;

    /**
     * Adds a row to the tracked stocks table with userId and stockId
     *
     * @param userId  the id associated with the user row
     * @param stockId the id associated with the stock row
     * @return true if added, false otherwise
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public boolean add(int userId, int stockId) throws PersistenceServiceException;

    /**
     * Checks if the row matching the userId and stockId exists in the
     * tracked stocks table
     *
     * @param userId  the id associated with the user row
     * @param stockId the id associated with the stock row
     * @return true if exists, false otherwise
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public boolean exists(int userId, int stockId) throws PersistenceServiceException;

    /**
     * Retrieves all tracked stocks associated with the userId
     *
     * @param  userId the id associated with the user row
     * @return a list of stocks or an empty list
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public List<String> get(int userId) throws PersistenceServiceException;

    /**
     * Deletes the row from the tracked stocks table associated
     * with the userId and stockId
     *
     * @param userId  the id associated with the user row
     * @param stockId the id associated with the stock row
     * @return true if successful, false otherwise
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public boolean delete(int userId, int stockId) throws PersistenceServiceException;

    /**
     * Deletes all tracked stocks associated with the userId
     *
     * @param  userId the id associated with the user
     * @return true if successful, false otherwise
     * @throws PersistenceServiceException this exception typically occurs when there is
     *           something wrong with the SQL statement or a connection is not available.
     *           The latter should not occur as checks should have already been done by
     *           the time this method is invoked.
     */
    public boolean deleteAll(int userId) throws PersistenceServiceException;
}
