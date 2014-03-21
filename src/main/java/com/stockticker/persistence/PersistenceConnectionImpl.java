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
      embedded H2 database only, for now.
     */
    private static final String H2_DRIVER = "org.h2.Driver";
    private static final String H2_URL = "jdbc:h2:";
    private static final String H2_IN_MEMORY = "mem:";
    private static final String H2_SCRIPT_CREATE = ";INIT=runscript from ";
    private static final String H2_SCRIPT_OPEN = ";IFEXISTS=TRUE";
    private static final String DEFAULT_PROPERTIES_FILE = "./config/stockticker.properties";
    private static final String SINGLE_QUOTE = "'";
    private static final String SEPARATOR = "/";

    private static final String IN_MEMORY = "1";
    private static final String DB_IN_MEMORY = "dbInMemory";
    private static final String DB_SCHEMA = "dbSchema";
    private static final String DB_NAME = "dbName";
    private static final String DB_LOCATION = "dbLocation";
    private static final String DB_USER = "dbUser";
    private static final String DB_PASSWORD = "dbPswd";

    private Connection connection;
    private boolean retryConnection = false;
    private String propertiesFile;

    //setup some default values in case one or more aren't provided in stockticker.properties
    private boolean dbInMemory = false;
    private String  dbSchema = "./sql/init_memory.sql";
    private String  dbName = "stockticker";
    private String  dbLocation = "data/mem";
    private String  dbUser = "sa";
    private String  dbPswd = "";

    /**
     * Invokes the start method with a properties file override.
     *
     * @param propertiesFileOverride an alternate properties file to use
     * @exception PersistenceServiceException provides message and error code
     *              for specific error situations
     */
    public void start(String propertiesFileOverride) throws PersistenceServiceException {
        this.propertiesFile = propertiesFileOverride;
        start();
    }

    /**
     * Sets up the database connection to the embedded H2 database engine. Refer to
     * the PersistenceServiceException for an explanation of the various error
     * codes.
     *
     * The connection URL is made up of the H2 URL (jdbc:h2:), the location of the database files e.g., in
     *   the data directory, the database name (default is stockticker), the script command and schema to
     *   run to initialize the database.
     *
     * To setup the database as in memory, i.e., the database doesn't not persist after the application
     *   closes, then set dbInMemory=1 in stockticker.properties. The default is 0, to persist the database.
     *
     * @exception PersistenceServiceException provides message and error code
     *              for specific error situations
     */
    public void start() throws PersistenceServiceException {

        //The do-while loop may loop a 2nd time to retry the connection
        // if a permanent database does not yet exist. The 2nd time around
        // the database will be created.
        do {
            //Load the database properties from the properties file and load
            // the database manager class.
            try {
                if (!retryConnection) {
                    loadProperties();
                }

                Class.forName(H2_DRIVER);
                String connectionUrl = buildConnectionUrl(retryConnection);
                connection = DriverManager.getConnection(connectionUrl, this.dbUser, this.dbPswd);
                retryConnection = false;
            }
            catch (IOException e) {
                int errorCode = PersistenceServiceException.PROPERTIES_FILE_NOT_FOUND;
                String message = PersistenceServiceException.PROPERTIES_FILE_NOT_FOUND_MESSAGE;
                throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
            }
            catch (ClassNotFoundException e) {
                int errorCode = PersistenceServiceException.DATABASE_DRIVER_NOT_FOUND;
                String message = PersistenceServiceException.DATABASE_DRIVER_NOT_FOUND_MESSAGE;
                throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
            }
            catch (SQLException e) {
                //if this is not a database not found condition, throw an exception.
                if (!e.getSQLState().equals(PersistenceServiceException.SQLSTATE_DATABASE_NOT_FOUND)) {
                    int errorCode = PersistenceServiceException.DATABASE_CONNECTION_FAILED;
                    String message = PersistenceServiceException.DATABASE_CONNECTION_FAILED_MESSAGE;
                    throw new PersistenceServiceException(message+" ["+errorCode+"]: "+e.getMessage(), e, errorCode);
                } else { //SQLSTATE="90013"
                    //if this is a database not found condition, retry the connection with sqlscript
                    // to create the database
                    retryConnection = true;
                }
            }
        } while (retryConnection);

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

    /*
     * Loads the database properties from the project properties file.
     *
     * Set default values for any properties that are either not provided
     * or empty.
     */
    private void loadProperties() throws IOException {
        if (propertiesFile == null) {
            propertiesFile = DEFAULT_PROPERTIES_FILE;
        }
        PropertiesFileReader properties = new PropertiesFileReader(propertiesFile);
        if (properties != null) {
            String tempDbInMemory = properties.getProperty(DB_IN_MEMORY);
            if (tempDbInMemory != null && !tempDbInMemory.isEmpty()) {
                this.dbInMemory = (tempDbInMemory.equals(IN_MEMORY));
            }
            String tempDbName = properties.getProperty(DB_NAME);
            if (tempDbName != null && !tempDbName.isEmpty()) {
                this.dbName = tempDbName;
            }
            String tempDbSchema = properties.getProperty(DB_SCHEMA);
            if (tempDbSchema != null && !tempDbSchema.isEmpty()) {
                this.dbSchema = tempDbSchema;
            }
            String tempDbLocation = properties.getProperty(DB_LOCATION);
            if (tempDbLocation != null && !tempDbLocation.isEmpty()) {
                this.dbLocation = tempDbLocation;
            }
            String tempDbUser = properties.getProperty(DB_USER);
            if (tempDbUser != null && !tempDbUser.isEmpty()) {
                this.dbUser = tempDbLocation;
            }
            String tempDbPswd = properties.getProperty(DB_PASSWORD);
            if (tempDbPswd != null && !tempDbPswd.isEmpty()) {
                dbPswd = tempDbPswd;
            }
        }
    }

    /*
     *  Builds the connection URL for both in memory and permanent databases.
     */
    private String buildConnectionUrl(boolean retry) {
        StringBuilder buffer = new StringBuilder();

        buffer.append(H2_URL);
        //the in-memory db doesn't require a db location and name
        if (this.dbInMemory) {
            buffer.append(H2_IN_MEMORY);
            buffer.append(H2_SCRIPT_CREATE);
        }
        else {
            /*initially, this logic will configure the connection
             url to attempt an open. If the open fails because the
             database doesn't exist, the connection string will be
             rebuilt using the database creation script.
             */
            buffer.append(dbLocation);
            buffer.append(SEPARATOR);
            buffer.append(dbName);
            if (retry) {
                buffer.append(H2_SCRIPT_CREATE);
                buffer.append(SINGLE_QUOTE);
                buffer.append(dbSchema);
                buffer.append(SINGLE_QUOTE);
            }
            else {
                buffer.append(H2_SCRIPT_OPEN);
            }
        }

        return buffer.toString();
    }

/*
    public static void main(String [] args) {

        PersistenceConnection connection = PersistenceConnectionImpl.INSTANCE;
        connection.start("./config/junittest.properties");
    }
*/

}
