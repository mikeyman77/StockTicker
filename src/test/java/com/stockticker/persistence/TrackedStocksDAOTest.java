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
    public void setUp() {
        userDAO.create(CONNALL, PASSWORD);
        userId = userDAO.getUserId(CONNALL);
        stockId = trackedDAO.getStockId(GOOG);
        trackedDAO.add(userId, stockId);
    }

    /**
     * Tears down the setUp environment
     */
    @After
    public void tearDown() {
        userDAO.delete(CONNALL);
        trackedDAO.deleteAll(userId);
    }

    /**
     * Tests that the getStockId method returns a valid stock Id
     */
    @Test
    public void testGetStockIdValid() {
        assertTrue("get valid stock id", (trackedDAO.getStockId(GOOG) > 0));
    }

    /**
     * Tests that the getStockId method returns an invalid stock Id
     */
    @Test
    public void testGetStockIdInvalid() {
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
    public void testAddTrue() {
        assertTrue("add tracked stock successful", (trackedDAO.add(userId, trackedDAO.getStockId(MSFT))));
    }

    /**
     * Test the add method for failure
     */
    @Test
    public void testAddFalse() {
        assertFalse("add tracked stock fails", (trackedDAO.add(userId, -1)));
    }

    /**
     * Tests the exists method to determine if the userId/stockId pair exists in
     * the tracked stocks table
     */
    @Test
    public void testExistsTrue() {
        assertTrue("tracked stock exists successful", (trackedDAO.exists(userId, trackedDAO.getStockId(GOOG))));
    }

    /**
     * Tests the exists method to determine if the userId/stockId pair does not exist in
     * the tracked stocks table
     */
    @Test
    public void testExistsFalse() {
        assertFalse("tracked stock exists fails", (trackedDAO.exists(userId, -1)));
    }

    /**
     * Tests the get method returns a list of tracked stocks
     */
    @Test
    public void testGetNotEmpty() {
        assertTrue("get tracked stocks not empty", (!trackedDAO.get(userId).isEmpty()));
    }

    /**
     * Tests the get method returns an empty
     */
    @Test
    public void testGetEmpty() {
        trackedDAO.deleteAll(userId);
        assertTrue("get tracked stocks not empty", (trackedDAO.get(userId).isEmpty()));
    }

    /**
     * Tests the delete method returns true
     */
    @Test
    public void testDeleteTrue() {
        assertTrue("delete tracked stocks true", (trackedDAO.delete(userId, stockId)));
    }

    /**
     * Tests the delete method returns false
     */
    @Test
    public void testDeleteFalse() {
        assertFalse("delete tracked stocks false", (trackedDAO.delete(userId, -1)));
    }

    /**
     * Tests the deleteAll method returns true
     */
    @Test
    public void testDeleteAllTrue() {
        assertTrue("delete all tracked stocks true", (trackedDAO.deleteAll(userId)));
    }

    /**
     * Tests the deleteAll method returns false
     */
    @Test
    public void testDeleteAllFalse() {
        assertFalse("delete all tracked stocks false", (trackedDAO.deleteAll(-1)));
    }

}
