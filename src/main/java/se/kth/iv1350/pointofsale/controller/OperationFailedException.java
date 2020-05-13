package se.kth.iv1350.pointofsale.controller;

/**
 * Thrown when an operation fails and the reason is unknown.
 */
public class OperationFailedException extends Exception {
    
    /**
     * Creates a new message with the specified message and cause.
     * 
     * @param message The exception message.
     * @param cause The causing exception.
     */
    public OperationFailedException(String message, Exception cause) {
        super(message, cause);
    }
}
