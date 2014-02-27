package com.stockticker;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * JUnit tests for the PropertiesFileReader class
 *
 * @author Stuart Connall
 * @version 1.0 - 2/26/14.
 */
public class PropertiesFileReaderTest {

    private PropertiesFileReader properties;
    private static final String PROP_FILE = "./config/stockticker.properties";
    private static final String DUMMY_PROP_FILE = "./config/junittest.properties";

    /**
     * Constructs PropertiesFileReader using stockticker.properties file
     */
    public PropertiesFileReaderTest() throws IOException {
        properties = new PropertiesFileReader(PROP_FILE);
    }

    /**
     * Tests that the constructor is successfully executed
     */
    @Test
    public void testPropertiesFileReaderConstructionValid() {
        assertNotNull("valid properties file", properties);
    }

    /**
     * Defines a rule for expected exceptions
     */
    //@Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Tests that an IOException occurred in the constructor
     */
    @Test
    public void testPropertiesFileReaderConstructionGetsIOException() throws IOException {

        thrown.expect(IOException.class);
        thrown.expectMessage("file not found");
        PropertiesFileReader properties = new PropertiesFileReader("");
    }

    /**
     * Tests that the getPropertyNames method returns a non-empty enumeration
     */
    @Test
    public void testGetPropertyNames() {
        assertTrue("get property names", properties.getPropertyNames().hasMoreElements());
    }

    /**
     * Tests that the getPropertyNames method returns a non-empty enumeration
     */
    @Test
    public void testGetPropertyNamesEmpty() throws IOException {
        PropertiesFileReader dummy = new PropertiesFileReader(DUMMY_PROP_FILE);
        assertFalse("no property names exist", dummy.getPropertyNames().hasMoreElements());
    }

    /**
     * Tests that the getProperty method returns a value
     */
    @Test
    public void testGetPropertyNotNull() {
        assertNotNull("get property success", properties.getProperty("dbName"));
    }

    /**
     * Tests that the getPropertyNames method returns a non-empty enumeration
     */
    @Test
    public void testGetPropertyNull() {
        assertNull("get property fail", properties.getProperty("noSuchThing"));
    }

}
