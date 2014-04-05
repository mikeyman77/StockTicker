/**
 * GUI for Stock Ticker Portfolio Manager
 * 90.308-061 Agile Software Dev. w/Java Project
 * Paul Wallace
 */

package com.stockticker.ui;

/**
 * main Screen for Stock Ticker Portfolio Manager
 * 90.308-061 Agile Software Dev. w/Java Project
 * @author prwallace
 */
public class StockTickerMain {
    
    public static void main( String[] args ) {
        ViewStockTicker gui = new ViewStockTicker();
        gui.build();
        gui.setPanels();
    }
}
