package com.stockticker.persistence;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Connection;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Tests the UserDAO interface methods
 *
 * @author Stuart Connall
 * @version 1.0 02/27/2014
 */
public class TrackedStocksDAOTest {

    private final UserDAO userDAO;
    private final TrackedStocksDAO trackedDAO;
    private static final String GOOG = "GOOG";
    private static final String MSFT = "MSFT";
    private static final String CONNALL = "connall";
    private static final String PASSWORD = "redsox";
    private int userId;
    private int stockId;


    public TrackedStocksDAOTest() throws PersistenceServiceException {
        this.userDAO = new UserDAOImpl();
        this.trackedDAO = new TrackedStocksDAOImpl();
    }

    /**
     * Sets up the required values in the database before each test
     */
    @Before
    public void setUp() throws PersistenceServiceException {
        userDAO.create(CONNALL, PASSWORD);
        userId = userDAO.getUserId(CONNALL);
        stockId = trackedDAO.getStockId(GOOG);
        trackedDAO.add(userId, stockId);
    }

    /**
     * Tears down the setUp environment
     */
    @After
    public void tearDown() throws PersistenceServiceException {
        //check to see if the PersistenceConnection service is active. This check is
        // required because of the Exception tests in this class disable the JDBC
        // connection in order to force an SQLException. If disabled, restart it.
        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        if (!persistenceConnection.connectionEstablished()) {
            persistenceConnection.start();
            UserDAOImpl.reestablishConnection();
            TrackedStocksDAOImpl.reestablishConnection();
        }
        userDAO.delete(CONNALL);
        trackedDAO.deleteAll(userId);
    }

    /**
     * Tests that the getStockId method returns a valid stock Id
     */
    @Test
    public void testGetStockIdValid() throws PersistenceServiceException {
        assertTrue("get valid stock id", (trackedDAO.getStockId(GOOG) > 0));
    }

    /**
     * Tests that the getStockId method returns an invalid stock Id
     */
    @Test
    public void testGetStockIdInvalid() throws PersistenceServiceException {
        assertFalse("get invalid stock id", (trackedDAO.getStockId("") > 0));
    }

    /**
     * Defines a rule for expected exceptions
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Tests if a PersistenceServiceException is thrown when getting a stock
     * by stock id
     *
     * @throws PersistenceServiceException
     */
    @Test
    public void testGetStockIdThrowsPersistenceServiceException() throws PersistenceServiceException {

        exception.expect(PersistenceServiceException.class);
        exception.expectMessage("An SQL Exception occurred in the Persistence Service");

        trackedDAO.getStockId("'");
    }

    /**
     * Test the add method that a tracked stock row is created with userId and
     * stockId
     */
    @Test
    public void testAddTrue() throws PersistenceServiceException {
        assertTrue("add tracked stock successful", (trackedDAO.add(userId, trackedDAO.getStockId(MSFT))));
    }

    /**
     * Test the add method for failure
     */
    @Test
    public void testAddFalse() throws PersistenceServiceException {
        assertFalse("add tracked stock fails", (trackedDAO.add(userId, -1)));
    }

    /**
     * Tests if a PersistenceServiceException is thrown when adding a tracked stock
     *
     * @throws PersistenceServiceException
     */
    @Test
    public void testAddThrowsPersistenceServiceException() throws PersistenceServiceException {

        exception.expect(PersistenceServiceException.class);
        exception.expectMessage("An SQL Exception occurred in the Persistence Service");

        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        if (persistenceConnection.connectionEstablished()) {
            persistenceConnection.closeConnection();
            trackedDAO.add(userId, -1);
        }
    }

    /**
     * Tests the exists method to determine if the userId/stockId pair exists in
     * the tracked stocks table
     */
    @Test
    public void testExistsTrue() throws PersistenceServiceException {
        assertTrue("tracked stock exists successful", (trackedDAO.exists(userId, trackedDAO.getStockId(GOOG))));
    }

    /**
     * Tests the exists method to determine if the userId/stockId pair does not exist in
     * the tracked stocks table
     */
    @Test
    public void testExistsFalse() throws PersistenceServiceException {
        assertFalse("tracked stock exists fails", (trackedDAO.exists(userId, -1)));
    }

    /**
     * Tests if a PersistenceServiceException is thrown when checking if a tracked stock exists
     *
     * @throws PersistenceServiceException
     */
    @Test
    public void testExistsThrowsPersistenceServiceException() throws PersistenceServiceException {

        exception.expect(PersistenceServiceException.class);
        exception.expectMessage("An SQL Exception occurred in the Persistence Service");

        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        if (persistenceConnection.connectionEstablished()) {
            persistenceConnection.closeConnection();
            trackedDAO.exists(userId, -1);
        }
    }

    /**
     * Tests the get method returns a list of tracked stocks
     */
    @Test
    public void testGetNotEmpty() throws PersistenceServiceException {
        assertTrue("get tracked stocks not empty", (!trackedDAO.get(userId).isEmpty()));
    }

    /**
     * Tests the get method returns an empty
     */
    @Test
    public void testGetEmpty() throws PersistenceServiceException {
        trackedDAO.deleteAll(userId);
        assertTrue("get tracked stocks not empty", (trackedDAO.get(userId).isEmpty()));
    }

    /**
     * Tests if a PersistenceServiceException is thrown when getting a tracked stock
     *
     * @throws PersistenceServiceException
     */
    @Test
    public void testGetThrowsPersistenceServiceException() throws PersistenceServiceException {

        exception.expect(PersistenceServiceException.class);
        exception.expectMessage("An SQL Exception occurred in the Persistence Service");

        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        if (persistenceConnection.connectionEstablished()) {
            persistenceConnection.closeConnection();
            trackedDAO.get(userId);
        }
    }

    /**
     * Tests the delete method returns true
     */
    @Test
    public void testDeleteTrue() throws PersistenceServiceException {
        assertTrue("delete tracked stocks true", (trackedDAO.delete(userId, stockId)));
    }

    /**
     * Tests the delete method returns false
     */
    @Test
    public void testDeleteFalse() throws PersistenceServiceException {
        assertFalse("delete tracked stocks false", (trackedDAO.delete(userId, -1)));
    }

    /**
     * Tests if a PersistenceServiceException is thrown when deleting a tracked stock
     *
     * @throws PersistenceServiceException
     */
    @Test
    public void testDeleteThrowsPersistenceServiceException() throws PersistenceServiceException {

        exception.expect(PersistenceServiceException.class);
        exception.expectMessage("An SQL Exception occurred in the Persistence Service");

        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        if (persistenceConnection.connectionEstablished()) {
            persistenceConnection.closeConnection();
            trackedDAO.delete(userId, -1);
        }
    }

    /**
     * Tests the deleteAll method returns true
     */
    @Test
    public void testDeleteAllTrue() throws PersistenceServiceException {
        assertTrue("delete all tracked stocks true", (trackedDAO.deleteAll(userId)));
    }

    /**
     * Tests the deleteAll method returns false
     */
    @Test
    public void testDeleteAllFalse() throws PersistenceServiceException {
        assertFalse("delete all tracked stocks false", (trackedDAO.deleteAll(-1)));
    }

    /**
     * Tests if a PersistenceServiceException is thrown when deleting all tracked stock
     *
     * @throws PersistenceServiceException
     */
    @Test
    public void testDeleteAllThrowsPersistenceServiceException() throws PersistenceServiceException {

        exception.expect(PersistenceServiceException.class);
        exception.expectMessage("An SQL Exception occurred in the Persistence Service");

        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        if (persistenceConnection.connectionEstablished()) {
            persistenceConnection.closeConnection();
            trackedDAO.deleteAll(-1);
        }
    }

}
