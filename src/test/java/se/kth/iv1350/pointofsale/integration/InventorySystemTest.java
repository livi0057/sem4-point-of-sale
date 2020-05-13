package se.kth.iv1350.pointofsale.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.pointofsale.model.ItemDTO;
import se.kth.iv1350.pointofsale.model.SaleDTO;
import se.kth.iv1350.pointofsale.model.InvalidItemIdException;
import se.kth.iv1350.pointofsale.integration.DatabaseException;
import se.kth.iv1350.pointofsale.model.Amount;

public class InventorySystemTest {

    @Test
    public void testGetItemInfoValidID () throws InvalidItemIdException, DatabaseException {
        int itemID = 1234;
        int quantity = 1;
        InventorySystem instance = new InventorySystem();
        String itemIDString = String.valueOf(itemID);
        
        try {
            instance.getItemInfo(itemID, quantity);
        }
        catch(InvalidItemIdException exc) {
            fail("Got Exception.");
            exc.printStackTrace();
        }
        catch (DatabaseException exc) {
            fail("Got Exception.");
            exc.printStackTrace();
        }
        ItemDTO expItem = new ItemDTO(1234, new Amount(20), 1, 25, "Pasta");
        String expResult = expItem.toString();
        ItemDTO resultInfo = instance.getItemInfo(itemID, quantity);
        String result = resultInfo.toString();
        assertEquals(expResult, result, "Could not get item information from valid item id.");
    }
    
   @Test
    public void testGetItemInfoInvalidID () throws InvalidItemIdException, DatabaseException {
        int itemID = 8888;
        int quantity = 1;
        InventorySystem instance = new InventorySystem();
        String itemIDString = String.valueOf(itemID);
        
        try {
            instance.getItemInfo(itemID, quantity);
        }
        catch(InvalidItemIdException exc) {
            assertTrue(exc.getMessage().contains(itemIDString), "Wrong exception message, "
                    + "does not contain specified item ID: " + exc.getMessage());
        }
        catch (DatabaseException exc) {
            fail("Got Exception.");
            exc.printStackTrace();
        }
    }
    
    @Test
    public void testGetItemInfoDbFailure () throws InvalidItemIdException, DatabaseException {
        int itemID = 9999;
        int quantity = 1;
        InventorySystem instance = new InventorySystem();
        String itemIDString = String.valueOf(itemID);
        
        try {
            instance.getItemInfo(itemID, quantity);
        }
        catch(DatabaseException exc) {
            assertTrue(exc.getMessage().contains("database"), "Wrong exception message, "
                    + "does not contain information about the exception: " + exc.getMessage());
        }
    }
}
