/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stockticker.ui;

/**
 *
 * @author Hunter97
 */
public class StockTicker_Main {
    
    public static void main( String[] args ) {
        ViewStockTicker gui = new ViewStockTicker();
        gui.build();
        gui.setPanels();
        gui.setVisible( true );
    }
}
