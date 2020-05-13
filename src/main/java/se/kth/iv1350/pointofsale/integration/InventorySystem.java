package se.kth.iv1350.pointofsale.integration;

import se.kth.iv1350.pointofsale.model.InvalidItemIdException;
import se.kth.iv1350.pointofsale.model.ItemDTO;
import se.kth.iv1350.pointofsale.model.Amount;
import se.kth.iv1350.pointofsale.model.SaleDTO;

/**
 * Contains all calls to the external accounting system.
 */
public class InventorySystem {
    /**
     * Creates a new instance.
     */
    InventorySystem() {
    }
    
    /**
     * Gets the item information from the external inventory system.
     * 
     * @param itemID The item id of the item to get from the external inventory system.
     * @param quantity The quantity of the item with the specified item id.
     * 
     * @throws InvalidItemIdException If the specified item id is invalid, that is does
     * not exist in the external inventory system.
     * @throws DatabaseException If the program can not connect to the database.
     * @return The specified items information.
     */
    public ItemDTO getItemInfo(int itemID, int quantity) throws InvalidItemIdException, DatabaseException {
        ItemDTO itemInfo = null;

        switch (itemID) {
            case 1234:
                Amount itemPrice = new Amount(20);
                itemInfo = new ItemDTO(itemID, itemPrice, quantity, 25, "Pasta");
                break;
            case 1212:
                itemPrice = new Amount(30);
                itemInfo = new ItemDTO(itemID, itemPrice, quantity, 12, "Flour");
                break;
            case 4321:
                itemPrice = new Amount(10);
                itemInfo = new ItemDTO(itemID, itemPrice, quantity, 6, "Milk ");
                break;
            case 9999:
                throw new DatabaseException("Can not connect to database.");
            default:
                throw new InvalidItemIdException(itemID);
        }
        return itemInfo;
    }
    
    /**
     * Updates the inventory system.
     * 
     * @param saleInfo The sale information used to update the inventory system
     */
    public void updateInventory(SaleDTO saleInfo) {
    }
    
}
