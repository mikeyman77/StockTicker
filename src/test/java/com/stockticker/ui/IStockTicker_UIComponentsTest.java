package com.stockticker.ui;

import com.stockticker.ui.IStockTicker_UIComponents.UI;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class IStockTicker_UIComponentsTest {


    @Test
    public void getNameTest() {
        assertEquals("Equal to Close", "Close", UI.CLOSE.getName());
        assertEquals("Equal to Registration", "Registration", UI.USER_REG.getName());
        assertEquals("Equal to Ticker", "Ticker", UI.TICKER.getName());
        assertEquals("Equal to Quote", "Quote", UI.QUOTE.getName());
        assertEquals("Equal to Home", "Home", UI.HOME.getName());
        assertEquals("Equal to Detail", "Detail", UI.DETAIL.getName());
        assertEquals("Equal to Login", "Login", UI.LOGIN.getName());
        assertEquals("Equal to Logout", "Logout", UI.LOGOUT.getName());
        assertEquals("Equal to Submit", "Submit", UI.SUBMIT.getName());
        assertEquals("Equal to Default", "Default", UI.DEFAULT.getName()); 
    }


    @Test
    public void getTypeTest() {
        assertEquals("Equal to CLOSE ", UI.getType("Close"), UI.CLOSE);
        assertEquals("Equal to USER_REG ", UI.getType("Registration"), UI.USER_REG);
        assertEquals("Equal to TICKER ", UI.getType("Ticker"), UI.TICKER);
        assertEquals("Equal to QUOTE ", UI.getType("Quote"), UI.QUOTE);
        assertEquals("Equal to HOME ", UI.getType("Home"), UI.HOME);
        assertEquals("Equal to DETAIL ", UI.getType("Detail"), UI.DETAIL);
        assertEquals("Equal to LOGIN ", UI.getType("Login"), UI.LOGIN);
        assertEquals("Equal to LOGOUT ", UI.getType("Logout"), UI.LOGOUT);
        assertEquals("Equal to SUBMIT ", UI.getType("Submit"), UI.SUBMIT);
        assertEquals("Equal to DEFAULT ", UI.getType("Default"), UI.DEFAULT);
    }

}
