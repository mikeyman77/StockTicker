
package com.stockticker.logic;

import com.stockticker.persistence.PersistenceService;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Business Logic Services class
 * 
 * @author Michael Grissom
 */
public class BusinessLogicServiceTest {
    
    private final BusinessLogicService instance;
    
    /**
     * Default constructor
     */
    public BusinessLogicServiceTest() {
        instance = BusinessLogicService.INSTANCE;
    }
    
    @After
    public void tearDown() throws Exception {
        // shutdown business logic service
        instance.stop();
    }
    
    /**
     * Test of start method under normal conditions.
     */
    @Test
    public void testStart() throws Exception {
        instance.start();
        boolean result = instance.isInitialized();
        assertTrue(result);
    }
    
    /**
     * Test of isInitialized method when the service is not initialized.
     */
    @Test
    public void testIsInitializedFalse() {
        boolean result = instance.isInitialized();
        assertFalse(result);
    }
    
    /**
     * Test of isInitialized method when the service is initialized.
     */
    @Test
    public void testIsInitializedTrue() {
        instance.start();
        boolean result = instance.isInitialized();
        assertTrue(result);
    }
    
    /**
     * Test of getUserAuth method under normal conditions.
     */
    @Test
    public void testGetUserAuth() {
        instance.start();
        AuthorizationService result = instance.getUserAuth();
        assertNotNull(result);
    }
    
    /**
     * Test of getUserAuth method when the service is shutdown.
     */
    @Test (expected=BusinessLogicException.class)
    public void testFailedGetUserAuth() {
        instance.getUserAuth();
    }
    
    /**
     * Test of getStockTicker method under normal conditions.
     */
    @Test
    public void testGetStockTicker() {
        instance.start();
        StockTickerService result = instance.getStockTicker();
        assertNotNull(result);
    }
    
    /**
     * Test of getStockTicker method when service is shutdown.
     */
    @Test (expected=BusinessLogicException.class)
    public void testFailedGetStockTicker() {
        instance.getStockTicker();
    }
    
    /**
     * Test of getPersistence method under normal conditions.
     */
    @Test
    public void testGetPersistence() {
        instance.start();
        PersistenceService result = instance.getPersistence();
        assertNotNull(result);
    }
    
    /**
     * Test of getPersistence method under normal conditions.
     */
    @Test (expected=BusinessLogicException.class)
    public void testFailedGetPersistence() {
        instance.getPersistence();
    }
}
