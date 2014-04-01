package com.stockticker.persistence;

import java.util.List;
import com.stockticker.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertNotNull;

/**
 * PersistenceService junit test case.
 *
 * @author  Stuart Connall
 * @version 1.0 02/12/2014
 */

public class PersistenceServiceTest {

    private PersistenceService persistence;
    private static final String PEDROIA = "Pedroia";
    private static final String ORTIZ = "Ortiz";
    private static final String VICTORINO = "Victorino";
    private static final String MANNING = "Manning";
    private static final String SCHILLING = "Schilling";
    private static final String CONNALL = "Connall";
    private static final String VEDDER = "Vedder";
    private static final String PASSWORD = "redsox";
    private static final String GOOG = "GOOG";
    private static final String AAPL = "AAPL";
    private static final String MSFT = "MSFT";
    private static final String ORCL = "ORCL";
    private User ortiz;
    private User vedder;

    /**
     * Constructs a PersistenceServiceTest instance and instantiates
     * and starts the PersistenceService.
     */
    public PersistenceServiceTest() {
        persistence = StockTickerPersistence.INSTANCE;
        persistence.start();
    }

    /**
     * Sets up each test before they run
     */
    @Before
    public void setUp() {
        persistence.createUser(PEDROIA, PASSWORD);
        ortiz = persistence.createUser(ORTIZ, PASSWORD);
        persistence.createUser(VICTORINO, PASSWORD);
        persistence.trackStock(ORTIZ, GOOG, true);
        persistence.trackStock(ORTIZ, AAPL, true);
        persistence.trackStock(ORTIZ, MSFT, true);
    }

    /**
     * Tears down setup
     */
    @After
    public void tearDown() {
        //persistence = StockTickerPersistence.INSTANCE;
        persistence.deleteUser(PEDROIA);
        persistence.deleteUser(ORTIZ);
        persistence.deleteUser(VICTORINO);
    }

    /**
     * Tests the getTrackedStocks method for a value.
     */
    @Test
    public void testGetTrackedStocks() {
        List<String> stocks = persistence.getTrackedStocks(ORTIZ);
        assertTrue("tracked stocks", (stocks.size() > 0));
    }

    /**
     * Tests the trackStock method returns true
     */
    @Test
    public void testTrackStockTrue() {
        assertTrue("track stock true", persistence.trackStock(ORTIZ, GOOG, true));
    }

    /**
     * Tests the trackStock method returns false
     */
    @Test
    public void testTrackStockFalse() {
        assertFalse("track stock false", persistence.trackStock(ORTIZ, "", false));
    }
    /**
     * Tests the isStockTracked method is true
     */
    @Test
    public void testIsStockTrackedTrue() {
        assertTrue("is stock tracked true", persistence.isStockTracked(ORTIZ, GOOG));
    }

    /**
     * Tests the isStockTracked method is false
     */
    @Test
    public void testIsStockTrackedFalse() {
        assertFalse("is stock tracked false", persistence.isStockTracked(ORTIZ, ORCL));
    }

    /**
     * Tests the userExists method is true
     */
    @Test
    public void testUserExistsTrue() {
        assertTrue("user exists", persistence.userExists(ORTIZ));
    }

    /**
     * Tests the userExists method is false
     */
    @Test
    public void testUserExistsFalse() {
        assertFalse("user doesn't exist", persistence.userExists(CONNALL));
    }

    /**
     * Tests the createUser method
     */
    @Test
    public void testCreateUser() {
        User user = persistence.createUser(SCHILLING, PASSWORD);
        assertNotNull("create user", user);
        persistence.deleteUser(SCHILLING);
    }

    /**
     * Tests the createUser method returns null when User exists
     */
    @Test
    public void testCreateUserReturnsNullIfExist() {
        User user = persistence.createUser(ORTIZ, PASSWORD);
        assertNull("create user when exists", user);
    }

    @Test
    public void testUpdateUserUpdatesPassword() {
        ortiz.setPassword(PASSWORD+"2014");
        persistence.updateUser(ortiz);
        assertEquals("update user updates password", PASSWORD + "2014", persistence.getUser(ORTIZ).getPassword());
    }

    @Test
    public void testUpdateUserTrue() {
        assertTrue("update user true", persistence.updateUser(ortiz));
    }

    @Test
    public void testUpdateUserFalse() {
        vedder = new User(VEDDER, PASSWORD);
        assertFalse("update user false", persistence.updateUser(vedder));
        persistence.deleteUser(VEDDER);
    }
      
    /**
     * Tests the getUser method for non null return
     */
    @Test
    public void testGetUserNotNull() {
        assertNotNull("load user not null", persistence.getUser(VICTORINO));
    }

    /**
     * Tests the getUser method for null return
     */
    @Test
    public void testGetUserNull() {
        vedder = new User(VEDDER, PASSWORD);
        assertNull("load user null", persistence.getUser(VEDDER));
    }

    /**
     * Tests the deleteUser method for return true
     */
    @Test
    public void testDeleteUserTrue() {
        assertTrue("delete user true", persistence.deleteUser(ORTIZ));
    }

    /**
     * Tests the deleteUser method for return false
     */
    @Test
    public void testDeleteUserFalse() {
        assertFalse("delete user false", persistence.deleteUser(VEDDER));
    }

    /**
     * Tests the setLoginStatus method for TRUE
     */
    @Test
    public void testSetLoginStatusTrue() {
        assertTrue("login status true", persistence.setLoginStatus(ORTIZ, true));
    }

    /**
     * Tests the setLoginStatus method for FALSE
     */
    @Test
    public void testSetLoginStatusUserNotFound() {
        assertFalse("login status false", persistence.setLoginStatus(VEDDER, true));
    }

    /**
     * Tests the isLoggedIn method for TRUE
     */
    @Test
    public void testIsLoggedInTrue() {
        persistence.setLoginStatus(ORTIZ, true);
        assertTrue("is logged in true", persistence.isLoggedIn(ORTIZ));
    }

    /**
     * Tests the isLoggedIn method for FALSE
     */
    @Test
    public void testIsLoggedInFalse() {
        persistence.setLoginStatus(ORTIZ, false);
        assertFalse("is logged in false", persistence.isLoggedIn(ORTIZ));
    }

    /**
     * Tests the getUserInfo method for non-null UserInfo
     */
    @Test
    public void testGetUserInfoNotNull() {
        assertNotNull("get userinfo not null", persistence.getUserInfo(ORTIZ));
    }

    /**
     * Tests the getUserInfo method for null UserInfo
     */
    @Test
    public void testGetUserInfoNull() {
        assertNull("get userinfo null", persistence.getUserInfo(MANNING));
    }
    /**
     * Tests the getLoggedInUsers method for empty list
     */
    @Test
    public void testGetLoggedInUsersReturnsList() {
        persistence.setLoginStatus(ORTIZ, true);
        persistence.setLoginStatus(PEDROIA, true);
        assertTrue("users logged in", persistence.getLoggedInUsers().size() > 0);
    }

    /**
     * Tests the getLoggedInUsers method for empty list
     */
    @Test
    public void testGetLoggedInUsersReturnsEmptyList() {
        List<String> users = persistence.getLoggedInUsers();
        for (String user : users) {
            persistence.setLoginStatus(user, false);
        }
        assertTrue("no users logged in", persistence.getLoggedInUsers().isEmpty());
    }

}