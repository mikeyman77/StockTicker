package com.stockticker.persistence;

import java.sql.Connection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.codehaus.dimple.Implementor;
import org.junit.rules.ExpectedException;

/**
 * Tests the PersistenceConnection class
 *
 * @author Stuart Connall
 * @version 1.0 2/16/14.
 */
public class PersistenceConnectionTest {

    private static final String INVALID_PROPERTIES_FILE = "./config/junittest.properties";

    /**
     * Perform environment initialization before each test is run
     */
    @Before
    public void setUp() throws PersistenceServiceException {
    }

    /**
     * Tests that a connection was established for inmemory database.
     */
    @Test
    public void testConnectionInMemoryDb() throws PersistenceServiceException {
        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        persistenceConnection.start("./config/tempdb_test.properties");
        assertTrue("connection established inMemory db", persistenceConnection.connectionEstablished());
    }

    /**
     * Tests that a connection was established for permanent database.
     */
    @Test
    public void testConnectionInPermanentDb() throws PersistenceServiceException {
        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        persistenceConnection.start("./config/permdb_test.properties");
        assertTrue("connection established permanent db", persistenceConnection.connectionEstablished());
    }

    /**
     * Tests that the start method establishes a valid jdbc Connection object
     */
    @Test
    public void testStart() throws PersistenceServiceException {
        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        persistenceConnection.start();
        assertNotNull("start establish connection", persistenceConnection.getConnection());
        persistenceConnection = null;
    }

    /**
     * Defines a rule for expected exceptions
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Tests if a PersistenceServiceException is thrown loading the properties file
     *
     * @throws PersistenceServiceException
     */
    @Test
    public void testStartThrowsPersistenceServiceExceptionPropertiesFileNotFound() throws PersistenceServiceException {

        exception.expect(PersistenceServiceException.class);
        exception.expectMessage(PersistenceServiceException.PSE100_PROPERTIES_FILE_NOT_FOUND_MESSAGE);

        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        persistenceConnection.start("./config/imaginary.properties");
        persistenceConnection = null;
    }

    /**
     * Tests if a PersistenceServiceException is thrown when establishing a database connection
     *
     * @throws PersistenceServiceException
     */
/*
    @Test
    public void testStartThrowsPersistenceServiceExceptionDatabaseConnectionFailed() throws PersistenceServiceException {

        exception.expect(PersistenceServiceException.class);
        exception.expectMessage(PersistenceServiceException.DATABASE_CONNECTION_FAILED_MESSAGE);

        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        persistenceConnection.start("./config/junittest.properties");
        persistenceConnection = null;
    }
*/
}
