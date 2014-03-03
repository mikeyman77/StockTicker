package com.stockticker.logic;

import com.stockticker.User;
import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.StockTickerPersistence;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import org.junit.After;
import org.junit.Before;

public class UserAuthorizationTest {

    private final PersistenceService persistentence = StockTickerPersistence.INSTANCE;
    private final AuthorizationService userAuth = UserAuthorization.INSTANCE;

    private final String wrongPassword = "none";
    private final String newPassword = "newPass";
    
    private final User testUser = new User("test", "testpass");
    
    @After
    public void tearDown() {
        persistentence.deleteUser(testUser.getUserName());
    }
    
    @Test
    public void testLogIn() throws Exception {
        User user = persistentence.createUser(testUser.getUserName(), testUser.getPassword());
        boolean result = userAuth.logIn(user.getUserName(), user.getPassword());
        assertTrue("Successful Login Test", result);
    }

    @Test
    public void testFailedLogIn() throws Exception {
        User user = persistentence.createUser(testUser.getUserName(), testUser.getPassword());
        boolean result = userAuth.logIn(user.getUserName(), "");
        assertFalse("Failed Login Test", result);
    }
    
    @Test
    public void testNonRegisteredLogIn() {
        boolean result = userAuth.logIn(testUser.getUserName(), 
                                        testUser.getPassword());
        assertFalse("Log in with non registered user test", result);
    }

    @Test
    public void testLogOut() throws Exception {
        persistentence.createUser(testUser.getUserName(), testUser.getPassword());
        persistentence.setLoginStatus(testUser.getUserName(), true);
        boolean result = userAuth.logOut(testUser.getUserName());
        assertTrue("Log out test", result);
    }
    
    @Test
    public void testFailedLogOut() throws Exception {
        boolean result = userAuth.logOut(testUser.getUserName());
        assertFalse("Failed log out test", result);
    }

    @Test
    public void testIsLoggedIn() throws Exception {
        persistentence.createUser(testUser.getUserName(), testUser.getPassword());
        persistentence.setLoginStatus(testUser.getUserName(), true);
        boolean result = userAuth.isLoggedIn(testUser.getUserName());
        assertTrue("Is logged in test", result);
    }

    @Test
    public void testIsNotLoggedIn() throws Exception {
        persistentence.createUser(testUser.getUserName(), testUser.getPassword());
        persistentence.setLoginStatus(testUser.getUserName(), false);
        boolean result = userAuth.isLoggedIn(testUser.getUserName());
        assertFalse("Is not logged in test", result);
    }

    @Test
    public void testRegister() throws Exception {
        boolean result = userAuth.register(testUser.getUserName(), 
                                            testUser.getPassword());
        assertTrue("Register test", result);
    }
    
    @Test
    public void testFailedRegister() throws Exception {
        persistentence.createUser(testUser.getUserName(), testUser.getPassword());
        boolean result = userAuth.register(testUser.getUserName(), 
                                            testUser.getPassword());
        assertFalse("Failed user registeration test", result);
    }

    @Test
    public void testUnRegister() throws Exception {
        persistentence.createUser(testUser.getUserName(), testUser.getPassword());
        boolean result = userAuth.unRegister(testUser.getUserName());
        assertTrue("Unregister test", result);
    }
    
    @Test
    public void testFailedUnRegister() throws Exception {
        boolean result = userAuth.unRegister(testUser.getUserName());
        assertFalse("Unregister failed test", result);
    }

    @Test
    public void testIsRegistered() throws Exception {
        persistentence.createUser(testUser.getUserName(), testUser.getPassword());
        boolean result = userAuth.isRegistered(testUser.getUserName());
        assertTrue("User is registered test", result);
    }
    
    @Test
    public void testIsNotRegistered() throws Exception {
        boolean result = userAuth.isRegistered(testUser.getUserName());
        assertFalse("User is not registered test", result);
    }

    @Test
    public void testGetUserInfo() throws Exception {
        // will implement soon
    }

    @Test
    public void testChangePassword() throws Exception {
        persistentence.createUser(testUser.getUserName(), testUser.getPassword());
        persistentence.setLoginStatus(testUser.getUserName(), true);
        boolean result = userAuth.changePassword(testUser.getUserName(), testUser.getPassword(), newPassword);
        assertTrue("Successful change password", result);
    }
    
    @Test
    public void testFailedChangePassword() throws Exception {
        persistentence.createUser(testUser.getUserName(), testUser.getPassword());
        persistentence.setLoginStatus(testUser.getUserName(), true);
        boolean result = userAuth.changePassword(testUser.getUserName(), wrongPassword, newPassword);
        assertFalse("Failed change password (bad password)", result);
    }
    
    @Test
    public void testFailedLoggedoutChangePassword() throws Exception {
        persistentence.createUser(testUser.getUserName(), testUser.getPassword());
        persistentence.setLoginStatus(testUser.getUserName(), false);
        boolean result = userAuth.changePassword(testUser.getUserName(), testUser.getPassword(), newPassword);
        assertFalse("Failed change password (logged out user)", result);
    }
}
