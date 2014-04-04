
package com.stockticker.logic;

/**
 * This class handles exceptions for business logic errors.
 * 
 * @author Michael Grissom
 */
public class BusinessLogicException extends RuntimeException {
    
    /**
     * Default constructor.
     */
    public BusinessLogicException () {
    }
    
    /**
     * Constructor with message.
     * 
     * @param message exception message
     */
    public BusinessLogicException (String message) {
        super(message);
    }
    
    /**
     * Constructor with throwable.
     * 
     * @param cause cause of the exception
     */
    public BusinessLogicException (Throwable cause) {
        super(cause);
    }
    
    /**
     * Constructor with message and throwable.
     * 
     * @param message exception message
     * @param cause cause of the exception
     */
    public BusinessLogicException (String message, Throwable cause) {
        super(message, cause);
    }
}
