/**
 * GUI for Stock Ticker Portfolio Manager
 * J308 Project
 * Paul Wallace
 */

package com.stockticker.ui;



/**
 * J308
 * @author prwallace
 */
public class StockTicker_Main {
    
    public static void main( String[] args ) {
        ViewStockTicker gui = new ViewStockTicker();
        gui.build();
        gui.setPanels();
        //gui.displayUIProperties();
    }
}
