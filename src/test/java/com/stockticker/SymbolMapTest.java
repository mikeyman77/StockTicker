package com.stockticker;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * SymbolMap junit test case.
 *
 * @author  Stuart Connall
 * @version 1.0 02/15/2014
 */

public class SymbolMapTest {

    private static final long NUMBER_OF_SYMBOLS = 2799;

    /**
     * Tests the getSymbols method
     */
    @Test
    public void testGetSymbols() {
        assertEquals("map size", NUMBER_OF_SYMBOLS, SymbolMap.getSymbols().size());
    }

    /**
     * Tests the isValidSymbol method true
     */
    @Test
    public void testIsValidSymbol() {
        assertTrue("is valid symbol", SymbolMap.isValidSymbol("GOOG"));
    }

    /**
     * Tests the isValidSymbol method false
     */
    @Test
    public void testIsNotValidSymbol() {
        assertFalse("is NOT valid symbol", SymbolMap.isValidSymbol("XXXX"));
    }
}