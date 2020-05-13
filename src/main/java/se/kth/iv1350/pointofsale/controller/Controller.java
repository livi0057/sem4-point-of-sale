package se.kth.iv1350.pointofsale.controller;

import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.pointofsale.model.*;
import se.kth.iv1350.pointofsale.integration.*;

/**
 * This is the application's only controller. All calls to the model pass through this class.
 */
public class Controller {
    private Sale sale;
    private InventorySystem inventorySystem;
    private AccountingSystem accountingSystem;
    private CashRegister cashRegister;
    private Printer printer;
    private List<SaleObserver> saleObservers = new ArrayList<>();
    
    /**
     * Creates a new instance.
     * 
     * @param creator   Used to get all systems that handle database calls.
     * @param printer   Interface to printer.
     */
    public Controller (SystemCreator creator, Printer printer) {
        this.inventorySystem = creator.getInventorySystem();
        this.accountingSystem = creator.getAccountingSystem();
        this.cashRegister = new CashRegister();
        this.printer = printer;
    }
    
    /**
     * Starts a new sale. This method must be called before doing anything else during a sale. 
     */
    public void startNewSale() {
        sale = new Sale();
        sale.addSaleObservers(saleObservers);
    }
    
    /**
     * Enters a item in the current sale. If the item to be registered is already 
     * present in the current sale, the items quantity is updated.
     * 
     * @param itemID        This is the item identifier for the scanned item.
     * @param quantity      This is the quantity of the item scanned.
     * @throws InvalidItemIdException If the item id does not exist in the inventory system.
     * @throws OperationFailedException If unable to get the item information from the inventory system,
     * other than that the item id does not exist.
     * @throws IllegalStateException If this method is called before <code>startNewSale</code>.
     * @return The entered item's item information.
     */
    public SaleInfoDTO enterItem(int itemID, int quantity) throws InvalidItemIdException, 
            OperationFailedException {
        if(sale == null) {
            throw new IllegalStateException("Call to enterItem before starting a new sale.");
        }
        ItemDTO itemAlreadyScanned = sale.findItem(itemID);
        SaleInfoDTO infoToPresent;
        
        if(itemAlreadyScanned != null) {
            infoToPresent = registerExistingItem(itemAlreadyScanned, quantity);
        }
        else {
            infoToPresent = registerNewItem(itemID, quantity);
        }
        return infoToPresent;
    }
    
    /**
     * Updates the quantity of the already scanned item in the current sale.
     * 
     * @param itemAlreadyScanned    This is the item info of the already scanned item.
     * @param quantity              This is the quantity to update the already scanned item with.
     * @return The updated information of the already scanned item.
     */
    private SaleInfoDTO registerExistingItem (ItemDTO itemAlreadyScanned, int quantity) {
        sale.updateQuantity(itemAlreadyScanned, quantity);
        SaleInfoDTO itemInfo = new SaleInfoDTO(itemAlreadyScanned.getItemDescription(), 
                    itemAlreadyScanned.getPrice(), sale.getTotalInclVat(), quantity);
        return itemInfo;
    }
    
    /**
     * Registers a new item to the current sale.
     * 
     * @param itemID        This is the item ID of the scanned item.
     * @param quantity      This is the quantity of the scanned item.
     * @throws InvalidItemIdException If the item id does not exist in the inventory system.
     * @throws OperationFailedException If unable to get the item information from the inventory system,
     * other than that the item id does not exist.
     * @return The information of the new registered item.
     */
    private SaleInfoDTO registerNewItem (int itemID, int quantity) 
            throws InvalidItemIdException, OperationFailedException {
        
        try {
        ItemDTO newItemInfo = inventorySystem.getItemInfo(itemID, quantity);
        sale.registerItem(newItemInfo);
        SaleInfoDTO itemInfo = new SaleInfoDTO(newItemInfo.getItemDescription(), 
                    newItemInfo.getPrice(), sale.getTotalInclVat(), quantity);
        return itemInfo;
        }
        catch (DatabaseException exc) {
            throw new OperationFailedException("Can not get item information.", exc);
        }
    }
    
    /**
     * Ends the current sale. No new items can be added after this.
     * 
     * @throws IllegalStateException If this method is called before <code>startNewSale</code>.
     * @return The total price including VAT for entire sale.
     */
    public Amount endSale() {
        if(sale == null) {
            throw new IllegalStateException("Call to endSale before starting a new sale.");
        }
        return sale.getTotalInclVat();
    }
    
    /**
     * Handles sale payment. Updates cash register, inventory and accounting.
     * Calculates change. Prints receipt.
     * 
     * @param paidAmount    The cash payment payed by customer.
     * @return The calculated change to give to customer.
     */
    public Amount enterPaidAmount(Amount paidAmount) {
        if(sale.getTotalInclVat() == null) {
            throw new IllegalStateException("Call to enterPaidAmount before ending sale.");
        }
        CashPayment payment = new CashPayment (paidAmount);
        cashRegister.increaseBalance(payment);
        Amount change = sale.completeSale(payment);
        cashRegister.updateBalance(change);
        
        SaleDTO saleInfo = sale.getSaleInfo();
        inventorySystem.updateInventory(saleInfo);
        accountingSystem.updateAccounting(saleInfo);
        
        sale.printReceipt(printer, payment, change);
        
        return change;
    }
    
    /**
     * The specified observer will be notified when a payment for a sale has been made. 
     * 
     * @param obs The observer to notify.
     */
    public void addSaleObserver(SaleObserver obs) {
        saleObservers.add(obs);
    }
}
