package se.kth.iv1350.pointofsale.integration;

import se.kth.iv1350.pointofsale.model.SaleDTO;
/**
 * A Singleton that creates instances of the accounting system that contains all calls 
 * to the external accounting system.
 * @author Linneawikberg
 */
public class AccountingSystem {
    private static final AccountingSystem INSTANCE = new AccountingSystem();
    
    /**
     * Creates a new instance.
     */
    private AccountingSystem() {  
    }
    
    public static AccountingSystem getAccountingSystem() {
        return INSTANCE;
    }
    
    /**
     * Updates the accounting system.
     * 
     * @param saleInfo The sale information used to update the accounting system.
     */
    public void updateAccounting(SaleDTO saleInfo) {  
    }
    
}
