package com.stockticker.persistence;

import com.stockticker.PropertiesFileReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is responsible for creating and initializing the
 * database and providing connections.
 *
 * Created by stu on 2/16/14.
 */
public enum PersistenceConnection {
    INSTANCE;

    private static final String H2_DRIVER = "org.h2.Driver";
    private static final String H2_URL = "jdbc:h2:";
    private static final String H2_SCRIPT = ";INIT=runscript from ";
    private static final String PROPERTIES_FILE = "./config/stockticker.properties";
    private static final String SINGLE_QUOTE = "'";
    private static final String SEPARATOR = "/";

    private static final String DB_SCHEMA = "dbSchema";
    private static final String DB_NAME = "dbName";
    private static final String DB_LOCATION = "dbLocation";
    private static final String DB_USER = "dbUser";
    private static final String DB_PASSWORD = "dbPswd";

    private Connection connection;
    private boolean dbInitialized = false;

    private String dbSchema;
    private String dbName;
    private String dbLocation;
    private String dbUser;
    private String dbPswd;

    /**
     * Default constructor that grabs local properties to build the connection
     * url and runs the database schema if not yet exists.
     *
     * @exception ClassNotFoundException
     * @exception SQLException
     * @exception Exception
     */
    private PersistenceConnection() {

        try {
            loadProperties();
            Class.forName(H2_DRIVER);

            String connectionUrl = H2_URL+dbLocation+SEPARATOR+dbName+H2_SCRIPT+SINGLE_QUOTE+dbSchema+SINGLE_QUOTE;
            connection = DriverManager.getConnection(connectionUrl, dbUser, dbPswd);

        /* If tables don't exist then initialize the database */

            /* SDC - this code will go away once it is assured that the
                     database schema is only run when the DB does not exist.
                     Otherwise, the following code checks if the tables
                     exist, which would enable the schema to be run using
                     something like the MyBatis script runner in the
                     initializeDatabase method below.

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet result = meta.getTables(null, null, "user", null);
            //If not result, then tables have not been created yet
            if (!result.next()) {
                dbName = H2_URL+dbName;
                dbInitialized = initializeDatabase(dbName);
            }
            */
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the database properties from the project properties file
     */
    private void loadProperties() throws IOException {
        PropertiesFileReader properties = new PropertiesFileReader(PROPERTIES_FILE);
        dbSchema = properties.getProperty(DB_SCHEMA);
        dbName = properties.getProperty(DB_NAME);
        dbLocation = properties.getProperty(DB_LOCATION);
        dbUser = properties.getProperty(DB_USER);
        dbPswd = properties.getProperty(DB_PASSWORD);
    }

    /**
     * Get a connection to the database
     *
     * @return sql Connection object
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Initialize database using init.sql schema
     *
     * @param dbName Name of database for connection
     */
    public boolean initializeDatabase(String dbName) {
        if (dbName.equals(""))
            return false;

        return true;
    }

}
