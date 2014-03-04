/**
 * GUI for Stock Ticker Portfolio Manager
 * J308 Project
 * Paul Wallace
 * 
 */

package com.stockticker.ui;

import java.awt.Color;

/**
 * Inteface for ViewStockTicker UI class Provides constant fields for button,
 * Card names, and other constant fields. Provides a status message method to
 * display messages in the UI by classes outside the UI.
 * 
 * @author prwallace
 */
public interface IStockTicker_UIComponents {

    /**
     * Enumeration constants representing the buttons within the UI. Provides a
     * public getName method that returns the String value of this constant.
     * 
     */
    public enum UI {
        CLOSE("Close"), USER_REG("Registration"), TICKER("Ticker"), QUOTE(
                "Quote"), HOME("Home"), DETAIL("Detail"), LOGIN("Login"), LOGOUT(
                "Logout"), SUBMIT("Submit"), UPDATE("Update"), DEFAULT(
                "Default");

        private final String btnName;

        /**
         * Constructor call, creates an instance equivelant to the string
         * argument
         * 
         */
        private UI(String name) {
            this.btnName = name;
        }

        /**
         * Returns the value of this UI type as a String
         * 
         */
        public String getName() {
            return btnName;
        }

        /**
         * Returns the type of UI type verses its String value (i.e. CLOSE vs
         * "Close")
         * 
         */
        public static UI getType(String name) {
            UI temp = DEFAULT;

            for(UI s : UI.values()) {
                if(s.btnName.equals(name)) {
                    temp = s;
                    break;
                }
            }

            return temp;
        }
    }

    /**
     * Displays a status message in the GUI. The Color argument is based on the
     * the type of status, error or non-error. Also provides a Tooltip if
     * needed; this field can be set to null.
     * 
     * @param Color
     *            color - color to display text
     * @param String
     *            message - Message to display in status box
     * @param String
     *            tip - Tooltip message
     */
    public void setStatus(Color color, String message, String tip);
}
