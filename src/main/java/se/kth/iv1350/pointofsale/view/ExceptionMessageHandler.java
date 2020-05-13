package se.kth.iv1350.pointofsale.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Creates the exception messages to be printed to the user interface.
 */
public class ExceptionMessageHandler {
    
    /**
     * Prints out the exception message to the user interface.
     * @param message The message that will be printed. 
     */
    public void showExceptionMessage(String message) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(createTime());
        sb.append(", ERROR: ");
        sb.append(message);
        System.out.println(sb);
    }
    
    private String createTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return now.format(formatter);
    }
}
