package se.kth.iv1350.pointofsale.model;

/**
 * A listeners interface for receiving notifications about when a payment has been made.
 * The class that is intereseted in this notification implements this interface.
 */
public interface SaleObserver {
    /**
     * Invoked when a payement for a sale has been made.
     * 
     * @param total The total amount of the made payment.
     */
    void updateTotal(Amount total) ;
    
}
