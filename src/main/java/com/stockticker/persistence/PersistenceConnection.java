package com.stockticker.persistence;

import com.stockticker.PropertiesFileReader;

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

    private Connection conn;
    private boolean dbInitialized = false;

    private String dbSchema;
    private String dbName;
    private String dbLocation;
    private String dbUser;
    private String dbPswd;

    /**
     * Default constructor that initiates database initialization
     */
    private PersistenceConnection() {


        try {
            loadProperties();
            Class.forName(H2_DRIVER);

            String connectionUrl = H2_URL+dbLocation+SEPARATOR+dbName+H2_SCRIPT+SINGLE_QUOTE+dbSchema+SINGLE_QUOTE;
            conn = DriverManager.getConnection(connectionUrl, dbUser, dbPswd);

        /* If tables don't exist then initialize the database */

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet result = meta.getTables(null, null, "user", null);
            //If not result, then tables have not been created yet
            if (!result.next()) {
                dbName = H2_URL+dbName;
                dbInitialized = initializeDatabase(dbName);
            }
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

    private void loadProperties() {
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
        return conn;
    }

    /**
     * Initialize database using init.sql schema
     *
     * @param dbName Name of database for connection
     */
    public boolean initializeDatabase(String dbName) {
        if (dbName.equals(""))
            return false;

        /*
        SDC - this is just sample code that demonstrates how to create a table
        and insert some data. Once the data is inserted you can submit queries
        to look at the data. In this specification the create table statements
        will be housed in a .sql file.

        ex. stat.execute("runscript from 'init.sql'");

        ex. retrieving a statement object and issuing create table and inserting
            data.
        Statement stat = conn.createStatement();
        stat.execute(<create table statement goes hers>);
        stat.execute(<insert data here);

        ex. querying the database and displaying the data.
        ResultSet rs;
        rs = stat.executeQuery(<select statement goes here);
        while (rs.next()) {
            System.out.println(rs.getString("name"));
        }
        stat.close();
        conn.close();
        */
        return true;
    }

}
