package se.kth.iv1350.pointofsale.view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import se.kth.iv1350.pointofsale.model.Amount;

/**
 * Prints the total revenue and the total number of sales to a text file.
 */
public class FileTotalRevenueView extends TotalRevenueView {
    private static final String REVENUE_FILE_NAME = "totalRevenue.txt";
    private PrintWriter revenueFile;
    
    public FileTotalRevenueView() throws IOException {
        revenueFile = new PrintWriter(new FileWriter((REVENUE_FILE_NAME), false));
    }
    
     /**
     * Prints the total revenue and the total number of sales payed since the program
     * was started to a text file.
     * 
     * @param totalRevenue The total revenue to print.
     * @param numberOfSales The total number of sales that has been payed.
     */
    @Override
    protected void printCurrentRevenue(Amount totalRevenue, int numberOfSales) {
        StringBuilder sb = new StringBuilder();
        sb.append(createTime());
        sb.append(", Completed sales: ");
        sb.append(numberOfSales);
        sb.append("\n     Current total revenue: ");
        sb.append(totalRevenue.toString());
        revenueFile.println(sb);
        revenueFile.flush();
    }

    private String createTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return now.format(formatter);
    }
}
