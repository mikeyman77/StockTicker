package com.stockticker.persistence;

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
    //private static final String H2_DBNAME = "jdbc:h2:/!!UMass/AgileJava/git/StockTicker/data/stockticker";
    //private static final String H2_URL = H2_DBNAME+";INIT=runscript from '/!!UMass/AgileJava/git/StockTicker/sql/init_cached.sql'";
    private static final String H2_DBNAME = "jdbc:h2:mem/stockticker";
    private static final String H2_URL = H2_DBNAME+";INIT=runscript from './sql/init_memory.sql'";

    private Connection conn;
    private boolean dbInitialized = false;

    /**
     * Default constructor that initiates database initialization
     */
    private PersistenceConnection() {

        try {
            Class.forName(H2_DRIVER);
            conn = DriverManager.getConnection(H2_URL, "sa", "");

        /* If tables don't exist then initialize the database */

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet result = meta.getTables(null, null, "user", null);
            //If not result, then tables have not been created yet
            if (!result.next()) {
                dbInitialized = initializeDatabase(H2_DBNAME);
            }
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
