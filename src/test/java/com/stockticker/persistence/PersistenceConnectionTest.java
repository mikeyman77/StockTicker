package com.stockticker.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.codehaus.dimple.Implementor;

/**
 * Tests the PersistenceConnection class
 *
 * @author Stuart Connall
 * @version 1.0 2/16/14.
 */
public class PersistenceConnectionTest {

    private static final String H2_DBNAME = "jdbc:h2:~/test";

    private PersistenceConnection persistence;

    /**
     * Perform environment initialization before each test is run
     */
    @Before
    public void setUp() {
        persistence = PersistenceConnection.INSTANCE;
    }

    /**
     * Tests that the returned java.sql.Connection is not null. This test
     * uses the Dimple library from Codehaus.org that enables you to override
     * JDBC interfaces such as Connection, without overriding all (or any)
     * of the interfaces methods.
     */
    @Test
    public void testConnection() {
        Connection stub = Implementor.proxy(Connection.class, new Object(){
        });
        assertNotNull("db connection", stub);
    }

    /**
     * tests that the database was succuessfully initialized
     */
    @Test
    public void testInitializeDatabaseTrue() {
        boolean initialized = persistence.initializeDatabase(H2_DBNAME);
        assertTrue("initialze db", initialized);
    }

    /**
     * tests that the database was not initialized
     */
    @Test
    public void testInitializeDatabaseFalse() {
        boolean initialized = persistence.initializeDatabase("");
        assertFalse("initialze db", initialized);
    }

}
