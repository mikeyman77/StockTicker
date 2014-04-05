/**
 * GUI for Stock Ticker Portfolio Manager
 * 90.308-061 Agile Software Dev. w/Java Project
 * Paul Wallace
 */
package com.stockticker.ui;

import com.stockticker.ui.IComponentsUI.UI;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * IComponentsUITest for interface IComponentUI.java,
 * Test the getName and getType methods in the enum UI.
 * @author prwallace
 */
public class IComponentsUITest {


    /**
     * Tests the getName method in IComponentUI.  This method returns the String
     * value of the enum UI field.  Test verifies the method returns the String
     * value of the UI field compared to is known value.
     */
    @Test
    public void getNameTest() {
        assertEquals("Equal to Close ", "Close", UI.CLOSE.getName());
        assertEquals("Equal to Registration ", "Registration", UI.USER_REG.getName());
        assertEquals("Equal to Track ", "Track", UI.TRACK.getName());
        assertEquals("Equal to Quote ", "Quote", UI.QUOTE.getName());
        assertEquals("Equal to Home ", "Home", UI.HOME.getName());
        assertEquals("Equal to History ", "History", UI.HISTORY.getName());
        assertEquals("Equal to Login ", "Login", UI.LOGIN.getName());
        assertEquals("Equal to Logout ", "Logout", UI.LOGOUT.getName());
        assertEquals("Equal to Submit ", "Submit", UI.SUBMIT.getName());
        assertEquals("Equal to Untrack ", "Untrack", UI.UNTRACK.getName());
        assertEquals("Equal to Refresh ", "Refresh", UI.REFRESH.getName());
        assertEquals("Equal to Ticker ", "Ticker", UI.TICKER.getName());
        assertEquals("Equal to Profile ", "Profile", UI.PROFILE.getName());
        assertEquals("Equal to Update ", "Update", UI.UPDATE.getName());
        assertEquals("Equal to Delete ", "Delete", UI.DELETE.getName());
        assertEquals("Equal to Default ", "Default", UI.DEFAULT.getName()); 
    }


    /**
     * Tests the getType method in IComponentUI.  This method returns the UI field
     * based on the String argument in the method.  Test verifies the method returns
     * the UI field based on its known constant.
     */
    @Test
    public void getTypeTest() {
        assertEquals("Equal to CLOSE ", UI.getType("Close"), UI.CLOSE);
        assertEquals("Equal to USER_REG ", UI.getType("Registration"), UI.USER_REG);
        assertEquals("Equal to TRACK ", UI.getType("Track"), UI.TRACK);
        assertEquals("Equal to QUOTE ", UI.getType("Quote"), UI.QUOTE);
        assertEquals("Equal to HOME ", UI.getType("Home"), UI.HOME);
        assertEquals("Equal to HISTORY ", UI.getType("History"), UI.HISTORY);
        assertEquals("Equal to LOGIN ", UI.getType("Login"), UI.LOGIN);
        assertEquals("Equal to LOGOUT ", UI.getType("Logout"), UI.LOGOUT);
        assertEquals("Equal to SUBMIT ", UI.getType("Submit"), UI.SUBMIT);
        assertEquals("Equal to UNTRACK ", UI.getType("Untrack"), UI.UNTRACK);
        assertEquals("Equal to REFRESH ", UI.getType("Refresh"), UI.REFRESH);
        assertEquals("Equal to TICKER ", UI.getType("Ticker"), UI.TICKER);
        assertEquals("Equal to PROFILE ", UI.getType("Profile"), UI.PROFILE);
        assertEquals("Equal to UPDATE ", UI.getType("Update"), UI.UPDATE);
        assertEquals("Equal to DELETE ", UI.getType("Delete"), UI.DELETE);
        assertEquals("Equal to DEFAULT ", UI.getType("Default"), UI.DEFAULT);
    }

}
