package se.kth.iv1350.pointofsale.view;

import se.kth.iv1350.pointofsale.model.SaleObserver;
import se.kth.iv1350.pointofsale.model.Amount;

/**
 * Shows the running total revenue when a payment has been made. Total revenue since the program was started.
 */
public abstract class TotalRevenueView implements SaleObserver {
    private int numberOfSales = 0;
    private Amount totalRevenue;
    
    /**
     * Creates a new instance. 
     */
    protected TotalRevenueView() {
        totalRevenue = new Amount();
    }
    
    /**
     * Updates the total revenue.
     * @param total The Amount to add to the total revenue.
     */
    @Override
    public void updateTotal(Amount total) {
        numberOfSales++;
        totalRevenue = totalRevenue.add(total);
        printCurrentRevenue(totalRevenue, numberOfSales);
    }
  
    /**
     * Shows the current total revenue since the program was started.
     * 
     * @param totalRevenue Contains the total revenue os all payed sales.
     * @param numberOfSales The number of payed sales.
     */
    protected abstract void printCurrentRevenue(Amount totalRevenue, int numberOfSales);
}
