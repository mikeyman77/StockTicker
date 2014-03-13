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
public enum PersistenceConnectionImpl implements PersistenceConnection {
    INSTANCE;

    /*
      The JDBC driver and url are static here and could be read from the
      properties file to allow connections to other database providers such
      as Oracle, DB2, MySQL, etc. However, this implementation calls for an
      embedded H2 database.
     */
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
     * Sets up the database connection to the embedded H2 database engine. Refer to
     * the PersistenceServiceException for an explanation of the various error
     * codes.
     *
     * @exception PersistenceServiceException provides message and error code
     *              for specific error situations
     */
    public void start() throws PersistenceServiceException {

        //Load the database properties from the properties file and load
        // the database manager class.
        try {
            loadProperties();
            Class.forName(H2_DRIVER);
            String connectionUrl = H2_URL+dbLocation+SEPARATOR+dbName+H2_SCRIPT+SINGLE_QUOTE+dbSchema+SINGLE_QUOTE;
            this.connection = DriverManager.getConnection(connectionUrl, this.dbUser, this.dbPswd);
        }
        catch (ClassNotFoundException e) {
            int errorCode = PersistenceServiceException.DATABASE_DRIVER_NOT_FOUND;
            String message = "Persistence Service unable to locate database driver";
            throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
        }
        catch (SQLException e) {
            int errorCode = PersistenceServiceException.DATABASE_CONNECTION_FAILED;
            String message = "Persistence Service unable to obtain database connection";
            throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
        }
        catch (IOException e) {
            int errorCode = PersistenceServiceException.PROPERTIES_FILE_NOT_FOUND;
            String message = "Persistence Service unable to open properties file";
            throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
        }
    }

    /**
     * Returns the active JDBC Connection. If the connection is
     * null, it means that the database connection has yet to be
     * establised. The PersistenceConnection's start method must
     * be invoked first.
     *
     * @return a JDBC Connection, otherwise null
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Determines if the connection has been established.
     *
     * @return true if connection established, false otherwise
     */
    public boolean connectionEstablished() {
        return (this.connection != null);
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
     * Initialize database using init.sql schema
     *
     * @param dbName Name of database for connection
     */
    private boolean initializeDatabase(String dbName) {
        if (dbName.equals(""))
            return false;

        return true;
    }

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
