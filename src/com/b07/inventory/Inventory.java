package com.b07.inventory;

import java.util.*;
import com.b07.inventory.Item;


public interface Inventory {
    public HashMap<Item,Integer> getItemMap();
    public void setItemMap(HashMap<Item,Integer> itemMap);
    public void updateMap(Item item, Integer value);
    public void setTotalItems(int total);
    public int getTotalItems();
}
