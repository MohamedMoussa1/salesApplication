package com.b07.inventory;

import java.util.*;
import com.b07.inventory.Item;

public class InventoryImpl implements Inventory {

    private int totalItems;
    private HashMap<Item,Integer> itemMap;
    
    @Override
    public HashMap<Item, Integer> getItemMap() {
        // TODO Auto-generated method stub
        return itemMap;
    }

    @Override
    public void setItemMap(HashMap<Item, Integer> itemMap) {
        // TODO Auto-generated method stub
        this.itemMap=itemMap;
    }

    @Override
    public void setTotalItems(int total) {
        // TODO Auto-generated method stub
        this.totalItems=total;
    }

    @Override
    public void updateMap(Item item, Integer value) {
        // TODO Auto-generated method stub
        this.itemMap.put(item,value);
    }   

    @Override
    public int getTotalItems(){
        return totalItems;
    }
    
}