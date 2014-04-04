package com.stockticker.persistence;

import java.sql.Connection;

/**
 * Defines the public interface for creating and accessing a JDBC
 * database connection.
 *
 * @author Stuart Connall
 * @see PersistenceConnectionImpl
 * @version 1.0 3/12/14.
 */
public interface PersistenceConnection {

    /**
     * Invokes the PersistenceConnection to perform initialization and
     * passes an alternate properties file to the initialize routine.
     *
     * @param  propertiesFileOverride an alternate properties file to use
     * @throws PersistenceServiceException
     */
    public void start(String propertiesFileOverride) throws PersistenceServiceException;

    /**
     * Invokes the PersistenceConnection to perform initialization.
     *
     * @throws PersistenceServiceException
     */
    public void start() throws PersistenceServiceException;

    /**
     * Returns the active JDBC Connection. If the connection is
     * null, it means that the database connection has yet to be
     * establised. The PersistenceConnection's start method must
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

}
