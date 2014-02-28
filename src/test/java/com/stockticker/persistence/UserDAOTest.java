package com.stockticker.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Tests the UserDAO interface methods
 *
 * @author Stuart Connall
 * @version 1.0 02/27/2014
 */
public class UserDAOTest {

    private final Connection connection = PersistenceConnection.INSTANCE.getConnection();
    private final UserDAO userDAO = new UserDAOImpl(this.connection);
    private static final String GOOG = "GOOG";
    private static final String MSFT = "MSFT";
    private static final String MALONE = "malone";
    private static final String PASSWORD = "bugsy";
    private int userId;

    /**
     * Sets up the required values in the database before each test
     */
    @Before
    public void setUp() {
        userDAO.create(MALONE, PASSWORD);
        userId = userDAO.getUserId(MALONE);
    }

    /**
     * Tears down the setUp environment
     */
    @After
    public void tearDown() {
        userDAO.delete(MALONE);
    }

    /**
     * Tests that the getUserId method returns a valid user Id
     */
    @Test
    public void testGetUserIdValid() {
        assertTrue("get valid user id", (userDAO.getUserId(MALONE) > 0));
    }

    /**
     * Tests that the getUserId method returns an invalid user Id
     */
    @Test
    public void testGetUserIdInvalid() {
        assertFalse("get invalid user id", (userDAO.getUserId("") > 0));
    }

}
