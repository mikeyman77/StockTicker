/*
 * 
 * 
 * 
 */

package com.stockticker.ui;



/**
 *
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
