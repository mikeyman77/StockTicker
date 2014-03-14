package com.stockticker.persistence;

import com.stockticker.User;
import com.stockticker.UserInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
    private static final String MALONE = "malone";
    private static final String BOJACKSON = "bojackson";
    private static final String PASSWORD = "bugsy";

    public UserDAOTest() throws PersistenceServiceException {
        this.userDAO = new UserDAOImpl();
    }

    /**
     * Sets up the required values in the database before each test
     */
    @Before
    public void setUp() {
        User user = userDAO.create(MALONE, PASSWORD);
        user.setUserInfo(new UserInfo("bugsy", "malone"));
        userDAO.update(user);
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

    /**
     * Tests that the create method returns a valid User object
     */
    @Test
    public void testCreateNewUser() {
        assertNotNull("create not null", userDAO.create(BOJACKSON, PASSWORD));
    }

    /**
     * Tests that the create method returns a valid User object
     */
    @Test
    public void testCreateUserAlreadyExists() {
        assertNull("create null", userDAO.create(MALONE, PASSWORD));
    }

    /**
     * Tests that the exists method returns true
     */
    @Test
    public void testExistsTrue() {
        assertTrue("exists true", userDAO.exists(MALONE));
    }

    /**
     * Tests that the exists method returns false
     */
    @Test
    public void testExistsFalse() {
        assertFalse("exists false", userDAO.exists(""));
    }

    /**
     * Tests that the update method returns true
     */
    @Test
    public void testUpdateTrue() {
        User user = userDAO.get(MALONE);
        user.setUserInfo(new UserInfo("xxxxxxx", "xxxxxxx"));
        assertTrue("update true", userDAO.update(user));
    }

    /**
     * Tests that the update method returns false
     */
    @Test
    public void testUpdateFalse() {
        assertFalse("update false", userDAO.update(new User("", PASSWORD)));
    }

    /**
     * Tests that the get method returns true
     */
    @Test
    public void testGetTrue() {
        assertNotNull("get not null", userDAO.get(MALONE));
    }

    /**
     * Tests that the get method returns false
     */
    @Test
    public void testGetFalse() {
        assertNull("get null", userDAO.get(""));
    }

    /**
     * Tests that the delete method returns true
     */
    @Test
    public void testDeleteTrue() {
        assertTrue("delete true", userDAO.delete(MALONE));
    }

    /**
     * Tests that the delete method returns false
     */
    @Test
    public void testDeleteFalse() {
        assertFalse("delete false", userDAO.delete(""));
    }

    /**
     * Tests that the isLoggedIn method returns true
     */
    @Test
    public void testIsLoggedInTrue() {
        userDAO.setLoginStatus(MALONE, true);
        assertTrue("is logged in true", userDAO.isLoggedIn(MALONE));
    }

    /**
     * Tests that the isLoggedIn method returns false
     */
    @Test
    public void testIsLoggedInFalse() {
        userDAO.setLoginStatus(MALONE, false);
        assertFalse("is logged in false", userDAO.isLoggedIn(MALONE));
    }

    /**
     * Tests that the setLoginStatus method returns true
     */
    @Test
    public void testSetLoginStatusTrue() {
        assertTrue("set login status true", userDAO.setLoginStatus(MALONE, true));
    }

    /**
     * Tests that the isLoggedIn method returns false
     */
    @Test
    public void testSetLoginStatusFalse() {
        assertFalse("set login status false", userDAO.setLoginStatus("", false));
    }

    /**
     * Tests that the getLoggedInUsers method returns a list
     */
    @Test
    public void testGetLoggedInUsersReturnsList() {
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
    public void testGetLoggedInUsersReturnsEmptyList() {
        List<String> users = userDAO.getLoggedInUsers();
        for (String user : users) {
            userDAO.setLoginStatus(user, false);
        }
        assertEquals("get logged in users empty list", 0, userDAO.getLoggedInUsers().size());
    }
}
