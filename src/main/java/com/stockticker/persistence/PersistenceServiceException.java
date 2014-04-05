package com.stockticker.persistence;

/**
 * <p>Exception handler for the Persistence Service.</p>
 *
 * @author Stuart Connall
 * @see Exception
 * @version 1.0 03/11/2014
 */
public class PersistenceServiceException extends Exception {

    /**
     * SQLState [90013] representing database not found
     */
    public static final String SQLSTATE_DATABASE_NOT_FOUND = "90013";

    /**
     * Error code [100] provided when the Stock Ticker properties file is not located.
     */
    public static final int    PSE100_PROPERTIES_FILE_NOT_FOUND = 100;

    /**
     * Error message provided when the Stock Ticker properties file is not located.
     */
    public static final String PSE100_PROPERTIES_FILE_NOT_FOUND_MESSAGE = "Persistence Service unable to open properties file";

    /**
     * Error code [200] provided when the JDBC database driver class is not located.
     */
    public static final int    PSE200_DATABASE_DRIVER_NOT_FOUND = 200;

    /**
     * Error message provided when the JDBC database driver class is not located.
     */
    public static final String PSE200_DATABASE_DRIVER_NOT_FOUND_MESSAGE = "Persistence Service unable to locate database driver";

    /**
     * Error code [201] provided when the JDBC database connection fails.
     */
    public static final int    PSE201_DATABASE_CONNECTION_FAILED = 201;

    /**
     * Error message provided when the JDBC database connection fails.
     */
    public static final String PSE201_DATABASE_CONNECTION_FAILED_MESSAGE = "Persistence Service unable to obtain database connection";

    /**
     * Error code [202] provided when an SQL Exception occurs during execution of SQL statement.
     */
    public static final int    PSE202_SQL_EXCEPTION_OCCURRED = 202;

    /**
     * Error message provided when an SQL Exception occurs during execution of SQL statement.
     */
    public static final String PSE202_SQL_EXCEPTION_OCCURRED_MESSAGE = "An SQL Exception occurred in the Persistence Service";

    private int errorCode;
    private int reasonCode;
    private int stateCode;

    /**
     * Constructs an exception class for the persistence service
     *
     * @param message    A detailed description of the exception
     * @param throwable  The cause of the exception
     * @param code       The error code associated with this exception
     */
    public PersistenceServiceException(String message, Throwable throwable, int code) {
        super(message, throwable);
        this.errorCode = code;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
