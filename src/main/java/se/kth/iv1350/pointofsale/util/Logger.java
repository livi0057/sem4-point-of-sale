package se.kth.iv1350.pointofsale.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * The Logger that logs the exceptions of interest to the developers in a text file.
 */
public class Logger {
    private static final String LOG_FILE_NAME = "pointofsale-log.txt";
    private PrintWriter logFile;
    
    public Logger() throws IOException {
        logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME), true);
    }
    
    /**
     * Logs information about a thrown exception.
     * 
     * @param exception The exception to log in the text file.
     */
    public void logException(Exception exception) {
        StringBuilder sb = new StringBuilder();
        sb.append(createTime());
        sb.append(", Exception was thrown: ");
        sb.append(exception.getMessage());
        logFile.println(sb);
        exception.printStackTrace(logFile);
    }
    
    private String createTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return now.format(formatter);
    }
    
}
