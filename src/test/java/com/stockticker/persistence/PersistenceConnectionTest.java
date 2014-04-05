package com.stockticker.persistence;

import java.sql.Connection;

import org.junit.After;
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

    private static final String NOSUCH_PROPERTIES_FILE = "./config/test/nosuch.properties";
    private static final String FAILDB_PROPERTIES_FILE = "./config/test/faildb_test.properties";
    private static final String TEMPDB_PROPERTIES_FILE = "./config/test/tempdb_test.properties";
    private static final String PERMDB_PROPERTIES_FILE = "./config/test/permdb_test.properties";

    /**
     * Perform environment initialization before each test is run
     */
    @Before
    public void setUp() throws PersistenceServiceException {
    }

    /**
     * Perform environment tear down
     */
    @After
    public void tearDown() throws PersistenceServiceException {
        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        if (persistenceConnection.connectionEstablished()) {
            persistenceConnection.closeConnection();
        }
    }

    /**
     * Tests that a connection was established for inmemory database.
     */
    @Test
    public void testConnectionInMemoryDb() throws PersistenceServiceException {
        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        persistenceConnection.start(TEMPDB_PROPERTIES_FILE);
        assertTrue("connection established inMemory db", persistenceConnection.connectionEstablished());
    }

    /**
     * Tests that a connection was established for permanent database.
     */
    @Test
    public void testConnectionInPermanentDb() throws PersistenceServiceException {
        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        persistenceConnection.start(PERMDB_PROPERTIES_FILE);
        assertTrue("connection established permanent db", persistenceConnection.connectionEstablished());
    }

    /**
     * Tests that the start method establishes a valid jdbc Connection object
     */
    @Test
    public void testStart() throws PersistenceServiceException {
        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        persistenceConnection.start(PERMDB_PROPERTIES_FILE);
        assertNotNull("start establish connection", persistenceConnection.getConnection());
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
        persistenceConnection.start(NOSUCH_PROPERTIES_FILE);
    }
}
