package com.stockticker;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

/**
 * SymbolMap junit test case.
 *
 * @author  Stuart Connall
 * @version 1.0 02/15/2014
 */

public class SymbolMapTest {

    private SymbolMap symbolMap;

    /**
     * Sets up each test before they run
     */
    @Before
    public void setUp() {
        symbolMap = SymbolMap.INSTANCE;
    }

    /**
     * Tests the getSymbols method
     */
    @Test
    public void testGetSymbols() {
        Map<String, String> symbols = symbolMap.getSymbols();
        for (Map.Entry<String, String> item : symbols.entrySet()) {
            System.out.println("Stock="+item.getKey()+" Description = "+item.getValue());
        }
    }

}