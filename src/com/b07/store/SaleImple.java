package com.b07.store;

import java.math.BigDecimal;
import java.util.HashMap;

import com.b07.inventory.Item;
import com.b07.users.User;

public class SaleImple implements Sale {

  int saleId;
  User user;
  BigDecimal price;
  HashMap<Item, Integer> itemMap;


  @Override
  public int getId() {
    // TODO Auto-generated method stub
    return saleId;
  }

  @Override
  public void setId(int id) {
    // TODO Auto-generated method stub
    this.saleId = id;
  }

  @Override
  public HashMap<Item, Integer> getItemMap() {
    // TODO Auto-generated method stub
    return itemMap;
  }

  @Override
  public BigDecimal getTotalPrice() {
    // TODO Auto-generated method stub
    return price;
  }

  @Override
  public User getUser() {
    // TODO Auto-generated method stub
    return user;
  }

  public void setItemMap(HashMap<Item, Integer> itemMap) {
    // TODO Auto-generated method stub
    this.itemMap = itemMap;
  }

  @Override
  public void setTotalPrice(BigDecimal price) {
    // TODO Auto-generated method stub
    this.price = price;
  }

  @Override
  public void setUser(User user) {
    // TODO Auto-generated method stub
    this.user = user;
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setName(String name) {
    // TODO Auto-generated method stub

  }

  @Override
  public BigDecimal getPrice() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setPrice(BigDecimal price) {
    // TODO Auto-generated method stub

  }

}
