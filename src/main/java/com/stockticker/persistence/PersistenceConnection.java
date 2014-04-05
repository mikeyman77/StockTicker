package com.stockticker.persistence;

import java.sql.Connection;

/**
 * <p>Defines the public interface for creating and accessing a JDBC
 * database connection.</p>
 *
 * @author Stuart Connall
 * @see PersistenceConnectionImpl
 * @see PersistenceServiceException
 * @version 1.0 3/12/14.
 */
public interface PersistenceConnection {

    /**
     * Invokes the PersistenceConnection to perform initialization and
     * passes an alternate properties file to the initialize routine.
     *
     * @param  propertiesFileOverride an alternate properties file to use
     * @throws PersistenceServiceException occurs when a failure occurs
     *         during PersistenceConnection initialization. Failures
     *         can be due to the service not locating the application
     *         properties file or a failure creating or connecting to
     *         the database.
     */
    public void start(String propertiesFileOverride) throws PersistenceServiceException;

    /**
     * Invokes the PersistenceConnection to perform initialization.
     *
     * @throws PersistenceServiceException occurs when a failure occurs
     *         during PersistenceConnection initialization. Failures
     *         can be due to the service not locating the application
     *         properties file or a failure creating or connecting to
     *         the database.
     */
    public void start() throws PersistenceServiceException;

    /**
     * Returns the active JDBC Connection. If the connection is
     * null, it means that the database connection has yet to be
     * established. The PersistenceConnection's start method must
     * be invoked first.
     *
     * @return a JDBC Connection, otherwise null
     */
    public Connection getConnection();

    /**
     * Determines if the connection has been established.
     *
     * @return true if connection established, false otherwise
     */
    public boolean connectionEstablished();

    /**
     * Closes the database connection
     *
     * @return true if connection successfully closed, false otherwise
     */
    public boolean closeConnection();

}
