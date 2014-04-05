package com.stockticker.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

/**
 * <p>Defines the data access methods for User related data. This class retrieves
 * an instance of the PersistenceConnection object and will initiate the start
 * sequence if a connection is not currently available.</p>
 *
 * @author Stuart Connall
 * @see TrackedStocksDAO
 * @see PersistenceConnection
 * @see PersistenceServiceException
 * @version 1.0 02/27/2014
 */
public class TrackedStocksDAOImpl implements TrackedStocksDAO {

    private static Connection connection;
    static final Logger logger = LogManager.getLogger(TrackedStocksDAOImpl.class.getName());

    /**
     * Reestablishes the database connection in the event the connection is lost and
     * restarted.
     */
    public static void reestablishConnection() {
        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        connection = persistenceConnection.getConnection();
    }

    /**
     * Constructs the Data Access Object for tracking stocks
     * and initiates the PersistenceConnection service and
     * retrieves a database connection.
     *
     * @throws PersistenceServiceException is thrown when a failure occurs in the PersistenceConnection
     * service such as failure to access the properties file or some sort of error during creation or
     * accessing of the database.
     */
    public TrackedStocksDAOImpl() throws PersistenceServiceException {
        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        if (!persistenceConnection.connectionEstablished()) {
            persistenceConnection.start();
        }
        connection = persistenceConnection.getConnection();

        //configure logg4j
        PropertyConfigurator.configure("./config/log4j.properties");
        logger.info("The stock tracking persistence component is ready for service.");
    }

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
    public int getStockId(String symbol) throws PersistenceServiceException {
        int stockId = -1;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT stockId FROM stock WHERE symbol='"+symbol+"'");
            if (result.next())
                stockId = result.getInt(1);
        }
        catch (SQLException e) {
            int errorCode = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED;
            String message = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE;
            logger.error(message, e);
            throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
        }

        return stockId;
    }

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
    @Override
    public boolean add(int userId, int stockId) throws PersistenceServiceException {
        boolean stockAdded = false;

        try {
            String query = "SELECT DISTINCT userId FROM user WHERE userId=? AND (SELECT stockId FROM stock WHERE stockId=?)";
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setInt(1, userId);
            prepared.setInt(2, stockId);
            ResultSet result = prepared.executeQuery();
            if (result.next()) {
                query = "INSERT INTO tracked_stock (userId, stockId) VALUES (?,?)";
                prepared = connection.prepareStatement(query);
                prepared.setInt(1, userId);
                prepared.setInt(2, stockId);
                if (prepared.executeUpdate() > 0) {
                    stockAdded = true;
                }
            }
        }
        catch (SQLException e) {
            int errorCode = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED;
            String message = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE;
            logger.error(message, e);
            throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
        }

        return stockAdded;
    }

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
    @Override
    public boolean exists(int userId, int stockId) throws PersistenceServiceException {
        boolean exists = false;
        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT trackId FROM tracked_stock WHERE userId='"+
                                                       userId+"' AND stockId='"+stockId+"'");
            if (result.next())
                exists = true;
        }
        catch (SQLException e) {
            int errorCode = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED;
            String message = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE;
            logger.error(message, e);
            throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
        }

        return exists;
    }

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
    @Override
    public List<String> get(int userId) throws PersistenceServiceException {
        List<String> trackedStocks = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT symbol FROM stock, tracked_stock "+
                    "WHERE stock.stockId=tracked_stock.stockId and tracked_stock.UserId='"+userId+"'");
            while (result.next()) {
                trackedStocks.add(result.getString(1));
            }
        }
        catch (SQLException e) {
            int errorCode = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED;
            String message = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE;
            logger.error(message, e);
            throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
        }

        return trackedStocks;
    }

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
    @Override
    public boolean delete(int userId, int stockId) throws PersistenceServiceException {
        boolean deleteSuccessful = false;
        try {
            //Update the User table
            String query = "DELETE FROM tracked_stock WHERE userId = ? AND stockId = ?";
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setInt(1, userId);
            prepared.setInt(2, stockId);
            if (prepared.executeUpdate() > 0) {
                deleteSuccessful = true;
            }
        }
        catch (SQLException e) {
            int errorCode = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED;
            String message = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE;
            logger.error(message, e);
            throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
        }

        return deleteSuccessful;
    }

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
    @Override
    public boolean deleteAll(int userId) throws PersistenceServiceException {
        boolean deleteSuccessful = true;
        try {
            //Update the User table
            String query = "DELETE FROM tracked_stock WHERE userId = ?";
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setInt(1, userId);
            if (prepared.executeUpdate() == 0)
                deleteSuccessful = false;
        }
        catch (SQLException e) {
            int errorCode = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED;
            String message = PersistenceServiceException.PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE;
            logger.error(message, e);
            throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
        }

        return deleteSuccessful;
    }

    private boolean isValidId(int id) {
        return (id > 0);
    }

}
