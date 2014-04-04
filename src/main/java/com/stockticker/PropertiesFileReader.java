package com.stockticker;

import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Reads a properties file from disk and loads into a Properties object
 *
 * @author Stuart Connall
 * @version 1.0 02/26/2014
 */
public class PropertiesFileReader {

    private Properties properties;

    /**
     * Constructs a Properties object using the contents
     * of filename argument.
     *
     * @param filename property file name
     */
    public PropertiesFileReader(String filename) throws IOException {

        FileReader reader = new FileReader(filename);
        properties = new Properties();
        properties.load(reader);
    }

    /**
     * Returns an enumeration of property keys
     *
     * @return property keys
     */
    public Enumeration<?> getPropertyNames() {
        return properties.propertyNames();
    }

    /**
     * Gets a property value by key
     *
     * @param property key
     * @return property value
     */
    public String getProperty(String property) {
        return properties.getProperty(property);
    }

}
