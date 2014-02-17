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
    private static final long NUMBER_OF_SYMBOLS = 2799;

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
        assertEquals("map size", NUMBER_OF_SYMBOLS, symbols.size());
    }

}