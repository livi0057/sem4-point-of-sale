package se.kth.iv1350.pointofsale.startup;

import java.io.IOException;
import se.kth.iv1350.pointofsale.integration.SystemCreator;
import se.kth.iv1350.pointofsale.controller.Controller;
import se.kth.iv1350.pointofsale.integration.Printer;
import se.kth.iv1350.pointofsale.view.View;


/**
 * Starts the entire application, contains the main method used to start the application.
 */
public class Main {
    /**
     * The main method used to start the entire application.
     *
     * @param args The application does not take any command line parameters.
     */
    public static void main(String[] args) {
        try {
        SystemCreator creator = new SystemCreator();
        Printer printer = new Printer();
        Controller contr = new Controller(creator, printer);
        View view = new View(contr);
        view.runFakeExecution();
        }
        catch(IOException exc) {
            System.out.println("Unable to start the application");
            exc.printStackTrace();
        }
    }
}
