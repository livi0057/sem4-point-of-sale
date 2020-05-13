package se.kth.iv1350.pointofsale.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.pointofsale.model.*;
import se.kth.iv1350.pointofsale.integration.*;

public class ControllerTest {
    private Controller instance;
    private SystemCreator systemCreator;
    ByteArrayOutputStream outContent;
    PrintStream originalSysOut;
    Sale sale;
    InventorySystem inventorySystem;
    
    @BeforeEach
    public void setUp() {
        originalSysOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        systemCreator = new SystemCreator();
        Printer printer = new Printer();
        instance = new Controller(systemCreator, printer);
    }
    
    @AfterEach
    public void tearDown() {
        outContent = null;
        System.setOut(originalSysOut);
        instance = null;
        systemCreator = null;
    }


    @Test
    public void testEnterExistingItem() throws InvalidItemIdException, OperationFailedException {
        int itemID = 1234;
        int quantity = 1;
        
        try {
            instance.startNewSale();
            instance.enterItem(itemID, quantity);
        }
        catch(InvalidItemIdException exc) {
            fail("Got Exception.");
            exc.printStackTrace();
        }
        SaleInfoDTO expItem = new SaleInfoDTO("Pasta", new Amount(20), new Amount(50), quantity);
        String expResult = expItem.toString();
        SaleInfoDTO resultInfo = instance.enterItem(itemID, quantity);
        String result = resultInfo.toString();
        assertEquals(expResult, result, "Did not find existing item.");
    }
    
    @Test
    public void testEnterNonExistingItem() throws InvalidItemIdException, OperationFailedException {
        int itemID = 8888;
        int quantity = 1;
        String itemIDString = String.valueOf(itemID);
        
        try {
            instance.startNewSale();
            SaleInfoDTO resultInfo = instance.enterItem(itemID, quantity);
            fail("Could enter item that does not exist in the inventory");
        }
        catch (InvalidItemIdException exc) {
            assertTrue(exc.getMessage().contains(itemIDString), "Wrong exception message, "
                    + "does not contain specified item ID: " + exc.getMessage());
        }
    }
    
    @Test
    public void testEnterItemWhenDbFails() throws InvalidItemIdException, OperationFailedException {
        int itemID = 9999;
        int quantity = 1;
        
        try {
            instance.startNewSale();
            SaleInfoDTO resultInfo = instance.enterItem(itemID, quantity);
            fail("Could enter item when no connection could be made to the database.");
        }
        catch (DatabaseException exc) {
            assertTrue(exc.getMessage().contains("database"), "Wrong exception message, does not contain"
                    + "information about the exception: " + exc.getMessage());
        }
        catch (OperationFailedException exc) {
            assertTrue(exc.getMessage().contains("item"), "Wrong exception message, does not contain"
                    + "information about the exception: " + exc.getMessage());
        }
    }
}
