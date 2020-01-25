package com.b07.users;

import java.util.HashMap;

import com.b07.inventory.Item;

public interface Account {
    public int getAccountId();
    public void setAccountId(int accountId);
    public User getUserOfAccount();
    public void setUserOfAccount(User userOfAccount);
    public HashMap<Item,Integer> getItemMap();
    public void setItemMap(HashMap<Item,Integer> itemMap);
}