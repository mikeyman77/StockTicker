
package com.stockticker.logic;

import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.PersistenceServiceException;
import com.stockticker.persistence.StockTickerPersistence;

/**
 * This class provides the functionality to initialize and get the services for 
 * the Business Logic.
 * 
 * @author Michael Grissom
 */
public enum BusinessLogicService {

    /**
     * Instance of business logic service.
     */
    INSTANCE;
    
    private boolean initialized;
    private boolean persistenceInitialized;
    private boolean userAuthIntialized;
    private boolean stockTickerIntialized;
    
    private UserAuthorization userAuth;
    private StockTicker stockTicker;
    private PersistenceService persistence;
    
    /**
     * Initializes the service.
     *
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public void start() throws BusinessLogicException {
        if (!initialized) {
            initPersistence();
            
            if (persistenceInitialized) {
                initUserAuth();
                initStockTicker();
            }
            
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
     * @throws com.stockticker.logic.BusinessLogicException
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
     * @throws com.stockticker.logic.BusinessLogicException
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
     * @throws com.stockticker.logic.BusinessLogicException
     */
    PersistenceService getPersistence() {
        if (!persistenceInitialized) {
            throw new BusinessLogicException("The business logic service has not been initialized!");
        }
        return persistence;
    }
    
    private void initPersistence() throws BusinessLogicException {
        persistence = StockTickerPersistence.INSTANCE;
        try {
            persistence.start();
        }
        catch (PersistenceServiceException ex) {
            throw new BusinessLogicException("Persistence service could not start.", ex);
        }
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
