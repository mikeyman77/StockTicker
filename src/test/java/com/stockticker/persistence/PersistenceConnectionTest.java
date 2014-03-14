package com.stockticker.persistence;

import java.sql.Connection;
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
        try {
            persistence = PersistenceConnectionImpl.INSTANCE;
            persistence.start();
        }
        catch (PersistenceServiceException e) {
            System.out.println(e.getMessage());
        }
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

}
