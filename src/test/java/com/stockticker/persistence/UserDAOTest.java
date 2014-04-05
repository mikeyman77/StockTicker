package com.stockticker.persistence;

import com.stockticker.User;
import com.stockticker.UserInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Tests the UserDAO interface methods
 *
 * @author Stuart Connall
 * @version 1.0 02/27/2014
 */
public class UserDAOTest {

    private final UserDAO userDAO;
    private static final String CONNALL = "connall";
    private static final String WILLIAMS = "williams";
    private static final String PASSWORD = "redsox";

    public UserDAOTest() throws PersistenceServiceException {
        this.userDAO = new UserDAOImpl();
    }

    /**
     * Sets up the required values in the database before each test
     */
    @Before
    public void setUp() throws PersistenceServiceException {
        User user = userDAO.create(CONNALL, PASSWORD);
        user.setUserInfo(new UserInfo("stuart", "connall"));
        userDAO.update(user);
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
        }
        userDAO.delete(CONNALL);
    }

    /**
     * Tests that the getUserId method returns a valid user Id
     */
    @Test
    public void testGetUserIdValid() throws PersistenceServiceException {
        assertTrue("get valid user id", (userDAO.getUserId(CONNALL) > 0));
    }

    /**
     * Tests that the getUserId method returns an invalid user Id
     */
    @Test
    public void testGetUserIdInvalid() throws PersistenceServiceException {
        assertFalse("get invalid user id", (userDAO.getUserId("") > 0));
    }

    /**
     * Defines a rule for expected exceptions
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Tests if a PersistenceServiceException is thrown when getting a user id
     *
     * @throws PersistenceServiceException
     */
    @Test
    public void testGetUserIdThrowsPersistenceServiceException() throws PersistenceServiceException {

        exception.expect(PersistenceServiceException.class);
        exception.expectMessage("An SQL Exception occurred in the Persistence Service");
        userDAO.getUserId("'");
    }

    /**
     * Tests that the create method returns a valid User object
     */
    @Test
    public void testCreateNewUser() throws PersistenceServiceException {
        assertNotNull("create not null", userDAO.create(WILLIAMS, PASSWORD));
        userDAO.delete(WILLIAMS);
    }

    /**
     * Tests that the create method returns a valid User object
     */
    @Test
    public void testCreateUserAlreadyExists() throws PersistenceServiceException {
        assertNull("create null", userDAO.create(CONNALL, PASSWORD));
    }

    /**
     * Tests if a PersistenceServiceException is thrown when creating a user
     *
     * @throws PersistenceServiceException
     */
    @Test
    public void testCreateUserThrowsPersistenceServiceException() throws PersistenceServiceException {

        exception.expect(PersistenceServiceException.class);
        exception.expectMessage("An SQL Exception occurred in the Persistence Service");

        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        if (persistenceConnection.connectionEstablished()) {
            persistenceConnection.closeConnection();
            userDAO.create("noname", "'");
        }
    }

    /**
     * Tests that the exists method returns true
     */
    @Test
    public void testExistsTrue() throws PersistenceServiceException {
        assertTrue("exists true", userDAO.exists(CONNALL));
    }

    /**
     * Tests that the exists method returns false
     */
    @Test
    public void testExistsFalse() throws PersistenceServiceException {
        assertFalse("exists false", userDAO.exists(""));
    }

    /**
     * Tests if a PersistenceServiceException is thrown when testing if a user exists
     *
     * @throws PersistenceServiceException
     */
    @Test
    public void testExistsThrowsPersistenceServiceException() throws PersistenceServiceException {

        exception.expect(PersistenceServiceException.class);
        exception.expectMessage("An SQL Exception occurred in the Persistence Service");
        userDAO.exists("'");
    }

    /**
     * Tests that the update method returns true
     */
    @Test
    public void testUpdateTrue() throws PersistenceServiceException {
        User user = userDAO.get(CONNALL);
        user.setUserInfo(new UserInfo("xxxxxxx", "xxxxxxx"));
        assertTrue("update true", userDAO.update(user));
    }

    /**
     * Tests that the update method returns false
     */
    @Test
    public void testUpdateFalse() throws PersistenceServiceException {
        assertFalse("update false", userDAO.update(new User("", PASSWORD)));
    }

    /**
     * Tests if a PersistenceServiceException is thrown when updating a user
     *
     * @throws PersistenceServiceException
     */
    @Test
    public void testUpdatesThrowsPersistenceServiceException() throws PersistenceServiceException {

        exception.expect(PersistenceServiceException.class);
        exception.expectMessage("An SQL Exception occurred in the Persistence Service");

        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        if (persistenceConnection.connectionEstablished()) {
            persistenceConnection.closeConnection();
            userDAO.update(new User("'", PASSWORD));
        }
    }

    /**
     * Tests that the get method returns true
     */
    @Test
    public void testGetTrue() throws PersistenceServiceException {
        assertNotNull("get not null", userDAO.get(CONNALL));
    }

    /**
     * Tests that the get method returns false
     */
    @Test
    public void testGetFalse() throws PersistenceServiceException {
        assertNull("get null", userDAO.get(""));
    }

    /**
     * Tests if a PersistenceServiceException is thrown when getting a user
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
            userDAO.get("'");
        }
    }

    /**
     * Tests that the delete method returns true
     */
    @Test
    public void testDeleteTrue() throws PersistenceServiceException {
        assertTrue("delete true", userDAO.delete(CONNALL));
    }

    /**
     * Tests that the delete method returns false
     */
    @Test
    public void testDeleteFalse() throws PersistenceServiceException {
        assertFalse("delete false", userDAO.delete(""));
    }

    /**
     * Tests if a PersistenceServiceException is thrown when deleting
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
            userDAO.delete("'");
        }
    }
    /**
     * Tests that the isLoggedIn method returns true
     */
    @Test
    public void testIsLoggedInTrue() throws PersistenceServiceException {
        userDAO.setLoginStatus(CONNALL, true);
        assertTrue("is logged in true", userDAO.isLoggedIn(CONNALL));
    }

    /**
     * Tests that the isLoggedIn method returns false
     */
    @Test
    public void testIsLoggedInFalse() throws PersistenceServiceException {
        userDAO.setLoginStatus(CONNALL, false);
        assertFalse("is logged in false", userDAO.isLoggedIn(CONNALL));
    }

    /**
     * Tests if a PersistenceServiceException is thrown when isLoggedIn is invoked
     *
     * @throws PersistenceServiceException
     */
    @Test
    public void testIsLoggedInThrowsPersistenceServiceException() throws PersistenceServiceException {

        exception.expect(PersistenceServiceException.class);
        exception.expectMessage("An SQL Exception occurred in the Persistence Service");

        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        if (persistenceConnection.connectionEstablished()) {
            persistenceConnection.closeConnection();
            userDAO.isLoggedIn("'");
        }
    }

    /**
     * Tests that the setLoginStatus method returns true
     */
    @Test
    public void testSetLoginStatusTrue() throws PersistenceServiceException {
        assertTrue("set login status true", userDAO.setLoginStatus(CONNALL, true));
    }

    /**
     * Tests that the isLoggedIn method returns false
     */
    @Test
    public void testSetLoginStatusFalse() throws PersistenceServiceException {
        assertFalse("set login status false", userDAO.setLoginStatus("", false));
    }

    /**
     * Tests if a PersistenceServiceException is thrown when setting the login status
     *
     * @throws PersistenceServiceException
     */
    @Test
    public void testSetLoginStatusThrowsPersistenceServiceException() throws PersistenceServiceException {

        exception.expect(PersistenceServiceException.class);
        exception.expectMessage("An SQL Exception occurred in the Persistence Service");

        PersistenceConnection persistenceConnection = PersistenceConnectionImpl.INSTANCE;
        if (persistenceConnection.connectionEstablished()) {
            persistenceConnection.closeConnection();
            userDAO.setLoginStatus("'", true);
        }
    }

    /**
     * Tests that the getLoggedInUsers method returns a list
     */
    @Test
    public void testGetLoggedInUsersReturnsList() throws PersistenceServiceException {
        List<String> users = userDAO.getLoggedInUsers();
        for (String user : users) {
            userDAO.setLoginStatus(user, true);
        }
        assertEquals("get logged in users", users.size(), userDAO.getLoggedInUsers().size());
    }

    /**
     * Tests that the getLoggedInUsers method returns an empty list
     */
    @Test
    public void testGetLoggedInUsersReturnsEmptyList() throws PersistenceServiceException {
        List<String> users = userDAO.getLoggedInUsers();
        for (String user : users) {
            userDAO.setLoginStatus(user, false);
        }
        assertEquals("get logged in users empty list", 0, userDAO.getLoggedInUsers().size());
    }
}
