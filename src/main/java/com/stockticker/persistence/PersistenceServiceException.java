package com.stockticker.persistence;

/**
 * Exception handler for the Persistence Service.
 *
 * @author Stuart Connall
 * @version 1.0 03/11/2014
 */
public class PersistenceServiceException extends RuntimeException {

    /**
     * Error code thrown when the Stock Ticker properties file is not located.
     */
    public static final int PROPERTIES_FILE_NOT_FOUND = 100;

    /**
     * Error code thrown when the JDBC database driver class is not loaced.
     */
    public static final int DATABASE_DRIVER_NOT_FOUND = 200;

    /**
     * Error code thrown when the JDBC database connection fails.
     */
    public static final int DATABASE_CONNECTION_FAILED = 201;

    /**
     * Error code thrown when an SQL Exception occurs during
     * execution of SQL statement.
     */
    public static final int SQL_EXCEPTION_OCCURRED = 202;

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
