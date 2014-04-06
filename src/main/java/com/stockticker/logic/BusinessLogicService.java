
package com.stockticker.logic;

import com.stockticker.persistence.PersistenceService;
import com.stockticker.persistence.PersistenceServiceException;
import com.stockticker.persistence.StockTickerPersistence;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

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
    
    private static final Logger logger = 
            LogManager.getLogger(BusinessLogicService.class.getName());
    
    private boolean initialized;
    private boolean persistenceInitialized;
    private boolean userAuthIntialized;
    private boolean stockTickerIntialized;
    
    private UserAuthorization userAuth;
    private StockTicker stockTicker;
    private PersistenceService persistence;

    private BusinessLogicService() {
        PropertyConfigurator.configure("./config/log4j.properties");
    }
    
    /**
     * Initializes the service.
     *
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public void start() throws BusinessLogicException {
        if (!initialized) {
            initPersistence(true);
            
            if (persistenceInitialized) {
                initUserAuth(true);
                initStockTicker(true);
            }
            
            if (persistenceInitialized && userAuthIntialized && stockTickerIntialized) {
                logger.info("Business logic service has been started");
                initialized =  true;
            }
        }
    }
    
    public void stop() throws BusinessLogicException {
        if (initialized) {
            initUserAuth(false);
            initStockTicker(false);
            initPersistence(false);
            
            if (!persistenceInitialized && !userAuthIntialized && !stockTickerIntialized) {
                logger.info("Business logic service has been stopped");
                initialized =  false;
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
            logger.error("The business logic service has not been started and initialized");
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
            logger.error("The business logic service has not been started and initialized");
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
            logger.error("The business logic service has not been started and initialized");
            throw new BusinessLogicException("The business logic service has not been initialized!");
        }
        return persistence;
    }
    
    private void initPersistence(boolean start) throws BusinessLogicException {
        if (start) {
            persistence = StockTickerPersistence.INSTANCE;
            
            try {
                persistence.start();
            }
            catch (PersistenceServiceException ex) {
                logger.error("Persistence service could not start", ex);
                throw new BusinessLogicException("Persistence service could not start", ex);
            }
            
            persistenceInitialized = true;
        }
        else {
            persistence = null;
            persistenceInitialized = false;
        }
    }
    
    private void initUserAuth(boolean start) {
        if (start) {
            userAuth = UserAuthorization.INSTANCE;
            userAuth.start();
            userAuthIntialized = true;
        }
        else {
            userAuth.stop();
            userAuth = null;
            userAuthIntialized = false;
        } 
    }
    
    private void initStockTicker(boolean start) {
        if (start) {
            stockTicker = StockTicker.INSTANCE;
            stockTicker.start();
            stockTickerIntialized = true;
        }
        else {
            stockTicker.stop();
            stockTicker = null;
            stockTickerIntialized = false;
        }
    }
}
