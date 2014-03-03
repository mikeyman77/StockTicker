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
     * @param filename
     */
    public PropertiesFileReader(String filename) {
        try {
            FileReader reader = new FileReader(filename);
            properties = new Properties();
            properties.load(reader);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            try {
                throw new IOException("file not found");
            }
            catch (IOException io) {

            }
        }
    }

    /**
     * Returns an enumuration of property keys
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

    public static void main(String [] args) {
        String key = null;
        PropertiesFileReader properties = new PropertiesFileReader("./config/stockticker.properties");
        for (Enumeration<?> property = properties.getPropertyNames(); property.hasMoreElements();) {
            key = (String) property.nextElement();
            System.out.println(key+"="+properties.getProperty(key));
        }

    }
}
