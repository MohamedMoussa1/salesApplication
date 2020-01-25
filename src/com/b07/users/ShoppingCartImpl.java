package com.b07.users;

import java.math.BigDecimal;
import java.util.HashMap;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.inventory.*;
import com.b07.exceptions.*;

public class ShoppingCartImpl implements ShoppingCart {

    private HashMap<Item, Integer> items;
    private Customer customer;
    private BigDecimal total;
    private final BigDecimal TAXRATE = new BigDecimal(1.13);

    public ShoppingCartImpl(Customer customerLogin) throws CustomerNotLoggedInException {
        if (!customerLogin.authenticated) {
            throw new CustomerNotLoggedInException();
        }

    }

    @Override
    public void addItemQuantity(int itemId, int newQuantity) {

        for (Item item : this.items.keySet()) {
            if (item.getId() == itemId) {
                int quantity = items.get(item);
                items.put(item, quantity + newQuantity);
                
            }
        }

    }

    @Override

    public void deleteItemQuantity(int itemId, int newQuantity) {
        for (Item item : this.items.keySet()) {
            if (item.getId() == itemId) {
                int quantity = items.get(item);
                if (newQuantity < quantity) {
                    items.put(item, quantity - newQuantity);
                }else{
                    System.out.println("Cannot remove items from the cart");
                }
            }
        }

    }

    

    @Override
    public HashMap<Item, Integer> getItems() {
        return items;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public BigDecimal getTaxRate() {
        return TAXRATE;
    }

    @Override
    public void clearCart() {
        items.clear();
    }

    private void totalPriceCalc() {

        for (Integer i : items.values()) {
            this.total = this.total.add(new BigDecimal(i));
        }

        this.total = this.total.add(this.total.multiply(this.TAXRATE));

    }

    public boolean checkOutCustomer() throws CustomerNotLoggedInException, ConnectionCorruptionException, LessThanZeroException {
        int inventoryQuantity;
        int cartQuantity;
        boolean cartContainsMoreItems = false;
        if (DatabaseSelectHelper.getUserRoleId(this.customer.getId()) == 3 && this.customer.authenticated) {
            this.totalPriceCalc();
        } else {
            throw new CustomerNotLoggedInException();
        }

        for (Item item : items.keySet()) {
            inventoryQuantity = DatabaseSelectHelper.getInventoryQuantity(item.getId());
            cartQuantity = items.get(item);
            if (cartQuantity > inventoryQuantity) {
                cartContainsMoreItems = true;
                break;
            }
        }
        if (cartContainsMoreItems) {
            return false;
        } else {
            int newQuantity;
            for (Item item : items.keySet()) {
                newQuantity = DatabaseSelectHelper.getInventoryQuantity(item.getId()) - items.get(item);
                DatabaseUpdateHelper.updateInventoryQuantity(newQuantity, item.getId());

            }
            this.clearCart();
            return true;
        }
    }

}