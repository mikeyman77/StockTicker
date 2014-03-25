
package com.stockticker.logic;

/**
 * This class handles exceptions for business logic errors.
 * 
 * @author Michael Grissom
 */
public class BusinessLogicException extends RuntimeException {
    
    public BusinessLogicException () {
    }
    
    public BusinessLogicException (String message) {
        super(message);
    }
    
    public BusinessLogicException (Throwable cause) {
        super(cause);
    }
    
    public BusinessLogicException (String message, Throwable cause) {
        super(message, cause);
    }
}
