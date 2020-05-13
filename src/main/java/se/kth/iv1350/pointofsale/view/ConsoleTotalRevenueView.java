package se.kth.iv1350.pointofsale.view;

import se.kth.iv1350.pointofsale.model.Amount;
/**
 * Prints the total revenue on the console.
 */
public class ConsoleTotalRevenueView extends TotalRevenueView{
    
    /**
     * Prints the total revenue and the total number of sales payed since the program
     * was started on the console.
     * 
     * @param totalRevenue The total revenue to print.
     * @param numberOfSales The total number of sales that has been payed.
     */
    @Override
    protected void printCurrentRevenue(Amount totalRevenue, int numberOfSales) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(dashedLine(20));
        sb.append(" DISPLAY ");
        sb.append(dashedLine(20));
        
        sb.append("\n");
        sb.append(insertSpace(12));
        sb.append("***** TOTAL REVENUE *****");
        sb.append(insertSpace(12));
        sb.append("\nTotal number of sales: ");
        sb.append(numberOfSales);
        sb.append("\nTotal revenue incl. VAT: ");
        sb.append(totalRevenue.toString());
        
        sb.append("\n");
        sb.append(dashedLine(17));
        sb.append(" END OF DISPLAY ");
        sb.append(dashedLine(17));
        sb.append("\n");
        
        System.out.println(sb.toString());
    }
    
    private String dashedLine(int number) {
        StringBuilder sb = new StringBuilder(number);
        for (int i = 0; i < number; i++)
            sb.append('-');
        return sb.toString();
    }
    
    private String insertSpace(int space) {
        StringBuilder sb = new StringBuilder(space);
        for (int i = 0; i < space; i++)
            sb.append(' ');
        return sb.toString();
    }
}
