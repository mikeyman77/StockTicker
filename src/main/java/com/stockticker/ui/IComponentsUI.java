/**
 * GUI for Stock Ticker Portfolio Manager
 * 90.308-061 Agile Software Dev. w/Java Project
 * Paul Wallace
 * 
 */

package com.stockticker.ui;


/**
 * Interface for ViewStockTicker UI class Provides constant fields for button,
 * Card names, and other constant fields. Provides a status message method to
 * display messages in the UI by classes outside the UI.
 * 
 * @author prwallace
 */
public interface IComponentsUI {

    /**
     * Enumeration constants representing the buttons within the UI. Provides a
     * public getName method that returns the String value of this constant.
     */
    public enum UI {
        CLOSE("Close"), USER_REG("Registration"), QUOTE("Quote"), HOME("Home"), LOGIN("Login"), LOGOUT("Logout"),
        SUBMIT("Submit"), TRACK("Track"), UNTRACK("Untrack"), REFRESH("Refresh"), HISTORY("History"), TICKER("Ticker"),
        PROFILE("Profile"), UPDATE("Update"), DELETE("Delete"), DEFAULT("Default");

        private final String btnName;

        /**
         * Constructor for enum UI
         * @param name  - the String value of this UI 
         */
        private UI(String name) {
            this.btnName = name;
        }

        /**
         * Returns the value of this UI type as a String
         * @return  - the string value for this UI type
         */
        public String getName() {
            return btnName;
        }

        /**
         * Returns the UI type verses its String value (i.e. CLOSE vs
         * "Close")
         * @param name  - String that is equal to one of the UI fields.
         * @return - returns the UI type that is equal to the argument String
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
     * Enumeration constants representing fields in the Login and Registration forms.
     */
    public enum Field {
        USER(0), PASSWD(1), FIRST_NM(2), VER_PASS(3), LAST_NM(4);
        
        private int value;

        /**
         * Constructor for enum Field
         * @param value - the value of the enum Field
         */
        private Field(int value) {
            this.value = value;
        }

        /**
         * Gets/returns the value of this Field
         * @return  - the integer value of this Field
         */
        public int getValue() {
            return value;
        }
        
    }
}
