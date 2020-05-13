package se.kth.iv1350.pointofsale.integration;

/**
 * Thrown when something goes wrong when trying to receive information from the database.
 * The message might contin more information about the error condition.
 */
public class DatabaseException extends RuntimeException {
    
    /**
     * Creates a new instance representing the condition described in the specified message.
     * 
     * @param message The message that describes the error.
     */
    public DatabaseException(String message) {
        super(message);
    }
}

