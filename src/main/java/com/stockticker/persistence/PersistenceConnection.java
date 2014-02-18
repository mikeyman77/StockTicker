package com.stockticker.persistence;

import java.sql.Connection;

/**
 * This class is responsible for creating and initializing the
 * database and providing connections.
 *
 * Created by stu on 2/16/14.
 */
public enum PersistenceConnection {
    INSTANCE;

    private static final String H2_DRIVER = "org.h2.Driver";
    private static final String H2_DBNAME = "jdbc:h2:~/test";

    private Connection conn;
    private boolean dbInitialized = false;

    /**
     * Default constructor that initiates database initialization
     */
    private PersistenceConnection() {
        //Class.forName(H2_DRIVER);
        //conn = DriverManager.getConnection(H2_DBNAME);

        /* If tables don't exist then initialize the database */

        if (!dbInitialized) {
            dbInitialized = initializeDatabase(H2_DBNAME);
        }
    }

    /**
     * Get a connection to the database
     *
     * @return sql Connection object
     */
    public Connection getConnection() {
        return null;
    }

    /**
     * Initialize database using init.sql schema
     *
     * @param dbName Name of database for connection
     */
    public boolean initializeDatabase(String dbName) {
        if (dbName.equals(""))
            return false;
        else
            return true;
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
    }

}
