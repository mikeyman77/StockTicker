
package com.stockticker.logic;

import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.StockTickerPersistence;

/**
 * This class provides the functionality to initialize and get the services for 
 * the Business Logic.
 * 
 * @author Michael Grissom
 */
public enum BusinessLogicService {
    INSTANCE;
    
    private boolean initialized;
    private boolean persistenceInitialized;
    private boolean userAuthIntialized;
    private boolean stockTickerIntialized;
    
    private UserAuthorization userAuth;
    private StockTicker stockTicker;
    private PersistenceService persistence;
    
    /**
     * Initializes the service
     */
    public void start() {
        if (!initialized) {
            initPersistence();
            
            if (persistenceInitialized) {
                initUserAuth();
                initStockTicker();
            }
            
            // TODO: detect  internet connection
            
            if (persistenceInitialized && userAuthIntialized && stockTickerIntialized) {
                initialized =  true;
            }
        }
    }
    
    /**
     * Checks if the business logic service has been initialized.
     * 
     * @return true if the service has been initialized
     */
    public boolean isInitialized() {
        return initialized;
    }
    
    /**
     * Gets the current User Authorization service.
     * 
     * @return AuthorizationService
     */
    public AuthorizationService getUserAuth() {
        if (!initialized && !userAuthIntialized) {
            throw new BusinessLogicException("The business logic service has not been initialized!");
        }
        return userAuth;
    }
    
    /**
     * Gets the current Stock Ticker service.
     * 
     * @return StockTickerService
     */
    public StockTickerService getStockTicker() {
        if (!initialized && !stockTickerIntialized) {
            throw new BusinessLogicException("The business logic service has not been initialized!");
        }
        return stockTicker;
    }
    
    /**
     * Gets the current Persistence service.
     * 
     * @return PersistenceService
     */
    PersistenceService getPersistence() {
        if (!persistenceInitialized) {
            throw new BusinessLogicException("The business logic service has not been initialized!");
        }
        return persistence;
    }
    
    private void initPersistence() {
        persistence = StockTickerPersistence.INSTANCE;
        persistence.start();
        persistenceInitialized = true;
    }
    
    private void initUserAuth() {
        userAuth = UserAuthorization.INSTANCE;
        userAuth.start();
        userAuthIntialized = true;
    }
    
    private void initStockTicker() {
        stockTicker = StockTicker.INSTANCE;
        stockTicker.start();
        stockTickerIntialized = true;
    }
}
