package com.b07.users;

import java.math.BigDecimal;
import java.util.HashMap;

import com.b07.inventory.Item;
import com.b07.exceptions.*;

public interface ShoppingCart {

    public HashMap<Item, Integer> getItems();

    public Customer getCustomer();

    public BigDecimal getTotal();

    public BigDecimal getTaxRate();

    public void clearCart();

    public void addItemQuantity(int itemId, int newQuantity);

    public void deleteItemQuantity(int itemId, int newQuantity);

    

    public boolean checkOutCustomer() throws CustomerNotLoggedInException, ConnectionCorruptionException, LessThanZeroException;
}