package se.kth.iv1350.pointofsale.view;

import se.kth.iv1350.pointofsale.controller.Controller;
import se.kth.iv1350.pointofsale.model.*;
import se.kth.iv1350.pointofsale.model.Sale;
import se.kth.iv1350.pointofsale.model.InvalidItemIdException;
import se.kth.iv1350.pointofsale.integration.DatabaseException;
import se.kth.iv1350.pointofsale.controller.OperationFailedException;
import se.kth.iv1350.pointofsale.util.Logger;
import java.io.IOException;

/**
 * This is a placeholder for the real view. It contains a hardcoded execution with calls to all
 * system operations in the controller.
 */
public class View {
    private Controller contr;
    private ExceptionMessageHandler exceptionMessageHandler = new ExceptionMessageHandler();
    private Logger logger;
    
    /**
     * Creates a new instance, that uses the specified controller for all calls to other layers.
     * 
     * @param contr The controller to use for all calls to other layers.
     */
    public View(Controller contr) throws IOException {
        this.contr = contr;
        contr.addSaleObserver(new ConsoleTotalRevenueView());
        contr.addSaleObserver(new FileTotalRevenueView());
        this.logger = new Logger();
    }
    
    /**
     * Performs a fake sale, by calling all system operations in the controller.
     */
    public void runFakeExecution() {
        contr.startNewSale();
        System.out.println("A new sale has been started.");
        
        enterItem(1234, 2);
        enterItem(1212, 2);
        enterItem(5555, 1);
        enterItem(1234, 1);
        enterItem(9999, 2);
        enterItem(4321, 3);
        
        Amount totalPrice = contr.endSale();
        System.out.println("\nThe sale is ended.");
        System.out.println("Total price incl. VAT: " + totalPrice);
        System.out.println("\n...PRINTING RECEIPT...");
        
        Amount paidAmount = new Amount (200);
        Amount change = contr.enterPaidAmount(paidAmount);
        System.out.println("Change: " + change.toString());
        
        contr.startNewSale();
        System.out.println("\nA new sale has been started.");
        
        enterItem(1234, 2);
        enterItem(4322, 1);
        enterItem(4321, 1);
        
        totalPrice = contr.endSale();
        System.out.println("\nThe sale is ended.");
        System.out.println("Total price incl. VAT: " + totalPrice);
        System.out.println("\n...PRINTING RECEIPT...");
        
        paidAmount = new Amount (100);
        change = contr.enterPaidAmount(paidAmount);
        System.out.println("Change: " + change.toString());
    }
    
    private void enterItem (int itemID, int quantity) {
        SaleInfoDTO infoToPresent;
        
        try {
            infoToPresent = contr.enterItem(itemID, quantity);
            System.out.print("\nAn item has been added to the current sale.");
            System.out.println(infoToPresent);
        }
        catch(InvalidItemIdException exc) {
            handleException("The entered item id (" + itemID + ") is invalid.", exc, false);
        }
        catch(OperationFailedException exc) {
            handleException("Can not connect to database. No item added.", exc, true);
        }
    }
    
    private void handleException(String message, Exception exc, boolean writeToLog) {
        exceptionMessageHandler.showExceptionMessage(message);
        if(writeToLog) {
            logger.logException(exc);
        }
    }    
}
