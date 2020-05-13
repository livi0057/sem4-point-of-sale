package se.kth.iv1350.pointofsale.model;

/**
 * Thrown when trying to get item information with an invalid item id.
 */
public class InvalidItemIdException extends Exception {
    private int invalidItemID;
    
    /**
     * Creates a new instance with a message that specifies which item id that is invalid.
     * 
     * @param invalidItemID The invalid item id.
     */
    public InvalidItemIdException (int invalidItemID) {
        super ("The entered item ID (" + invalidItemID + ") is invalid.");
        this.invalidItemID = invalidItemID;
    }
    
    /**
     * Gets the item id that is invalid.
     * 
     * @return The invalid item id.
     */
    public int getInvalidItemID () {
        return invalidItemID;
    }
}
