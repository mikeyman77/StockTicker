package com.stockticker.logic;

import com.stockticker.User;
import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.StockTickerPersistence;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import org.junit.Before;

public class UserAuthorizationTest {

    private final PersistenceService persistentence = StockTickerPersistence.INSTANCE;
    private final AuthorizationService userAuth = UserAuthorization.INSTANCE;

    private final String testUsername = "test";
    private final String testPassword = "password";
    private final String wrongPassword = "none";
    private final String newPassword = "newPass";
    
    private final User regUser = new User("mary", "password1");
    private final User nonRegUser = new User("john", "password2");
    private final User loggedInUser = new User("mark", "password3");
    private final User loggedOutUser = new User("susan", "password4");
    
    @Before
    public void setUp() {
        // create a registered user
        persistentence.createUser(regUser);
        
        // create logged out user
        persistentence.createUser(loggedOutUser);
        
        // create logged in user
        persistentence.createUser(loggedInUser);
        loggedInUser.setLoggedIn(true);
        persistentence.setLoginStatus(loggedInUser.getUserName(), true);
        
        // create logged out user
        persistentence.createUser(loggedOutUser);
        loggedInUser.setLoggedIn(false);
        persistentence.setLoginStatus(loggedOutUser.getUserName(), false);
    }
    
    @Test
    public void testLogIn() throws Exception {
        boolean result = userAuth.logIn(loggedOutUser.getUserName(), 
                                        loggedOutUser.getPassword());
        assertTrue("Successful Login Test", result);
    }

    @Test
    public void testFailedLogIn() throws Exception {
        boolean result = userAuth.logIn(loggedOutUser.getUserName(), "");
        assertFalse("Failed Login Test", result);
    }
    
    @Test
    public void testNonRegisteredLogIn() {
        boolean result = userAuth.logIn(nonRegUser.getUserName(), 
                                        nonRegUser.getPassword());
        assertFalse("Log in with non registered user test", result);
    }

    @Test
    public void testLogOut() throws Exception {
        boolean result = userAuth.logOut(loggedInUser.getUserName());
        assertTrue("Log out test", result);
    }
    
    @Test
    public void testFailedLogOut() throws Exception {
        boolean result = userAuth.logOut(loggedOutUser.getUserName());
        assertFalse("Failed log out test", result);
    }

    @Test
    public void testIsLoggedIn() throws Exception {
        boolean result = userAuth.isLoggedIn(loggedInUser.getUserName());
        assertTrue("Is logged in test", result);
    }

    @Test
    public void testIsNotLoggedIn() throws Exception {
        boolean result = userAuth.isLoggedIn(loggedOutUser.getUserName());
        assertFalse("Is not logged in test", result);
    }

    @Test
    public void testRegister() throws Exception {
        boolean result = userAuth.register(nonRegUser.getUserName(), 
                                            nonRegUser.getPassword());
        assertTrue("Register test", result);
    }
    
    @Test
    public void testFailedRegister() throws Exception {
        boolean result = userAuth.register(regUser.getUserName(), 
                                            regUser.getPassword());
        assertFalse("Failed user registeration test", result);
    }

    @Test
    public void testUnRegister() throws Exception {
        boolean result = userAuth.unRegister(regUser.getUserName());
        assertTrue("Unregister test", result);
    }
    
    @Test
    public void testFailedUnRegister() throws Exception {
        boolean result = userAuth.unRegister(nonRegUser.getUserName());
        assertFalse("Unregister failed test", result);
    }

    @Test
    public void testIsRegistered() throws Exception {
        boolean result = userAuth.isRegistered(regUser.getUserName());
        assertTrue("User is registered test", result);
    }
    
    @Test
    public void testIsNotRegistered() throws Exception {
        boolean result = userAuth.isRegistered(nonRegUser.getUserName());
        assertFalse("User is not registered test", result);
    }

    @Test
    public void testGetUserInfo() throws Exception {
        // will implement soon
    }

    @Test
    public void testChangePassword() throws Exception {
        String username = loggedInUser.getUserName();
        String password = loggedInUser.getPassword();
        boolean result = userAuth.changePassword(username, password, newPassword);
        assertTrue("Successful change password", result);
    }
    
    @Test
    public void testFailedChangePassword() throws Exception {
        String username = loggedInUser.getUserName();
        String password = loggedInUser.getPassword();
        boolean result = userAuth.changePassword(username, wrongPassword, newPassword);
        assertFalse("Failed change password (bad password)", result);
    }
    
    @Test
    public void testFailedLoggedoutChangePassword() throws Exception {
        String username = loggedOutUser.getUserName();
        String password = loggedOutUser.getPassword();
        boolean result = userAuth.changePassword(username, password, newPassword);
        assertFalse("Failed change password (logged out user)", result);
    }
}
