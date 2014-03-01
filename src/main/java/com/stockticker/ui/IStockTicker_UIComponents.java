/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stockticker.ui;

import java.awt.Color;


/**
 *
 * @author prwallace
 */
public interface IStockTicker_UIComponents {

    /**
     * Enumeration constants representing the buttons within the UI.  Provides a
     * public getName method that returns the String value of this constant.
     *
     */
    public enum  UI {

        CLOSE("Close"), USER_REG("Registration"), TICKER("Ticker"), QUOTE("Quote"), HOME("Home"), DETAIL("Detail"),
                LOGIN("Login"), LOGOUT("Logout"), SUBMIT("Submit"), UPDATE("Update"), DEFAULT("Default");

        private final String btnName;

        private UI( String name ) {
            this.btnName = name;
        }

        public String getName() {
            return btnName;
        }

        @Override
        public String toString() {
            return btnName;
        }

        public static UI getType( String name ) {
            UI temp = DEFAULT;

            for( UI s : UI.values() ) {
                if( s.btnName.equals( name ) ){
                    temp = s;
                    break;
                }
            }

            return temp;
        }
    }


    
    public void setStatus( Color color, String message, String tip );
}
