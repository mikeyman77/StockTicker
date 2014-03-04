/**
 * GUI for Stock Ticker Portfolio Manager
 * J308 Project
 * Paul Wallace
 */

package com.stockticker.ui;

import com.stockticker.logic.AuthorizationService;
import com.stockticker.logic.UserAuthorization;



/**
 * J308
 * @author prwallace
 */
public class StockTicker_Main {
    
    public static void main( String[] args ) {
        ViewStockTicker gui = new ViewStockTicker();
        gui.build();
        gui.setPanels();
        AuthorizationService m_userAuth = UserAuthorization.INSTANCE;  //Was in ViewSticlTicker, move here as a test, but still fails
        System.out.println("back in main");                            // 1st problem get "The System cannot find the path specificed"
    }                                                                  // If I add an absolute path, then I get Access Denied
}
