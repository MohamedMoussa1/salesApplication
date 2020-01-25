package com.b07.users;

import java.util.HashMap;

import com.b07.inventory.Item;

public class AccountImpl implements Account {

    int accountId;
    User userOfAccount;
    HashMap<Item,Integer> itemMap;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public User getUserOfAccount() {
        return userOfAccount;
    }

    public void setUserOfAccount(User userOfAccount) {
        this.userOfAccount = userOfAccount;
    }

    public HashMap<Item, Integer> getItemMap() {
        return itemMap;
    }

    public void setItemMap(HashMap<Item, Integer> itemMap) {
        this.itemMap = itemMap;
    }
    
}