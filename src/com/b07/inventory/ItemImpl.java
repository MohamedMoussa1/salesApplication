package com.b07.inventory;

import java.math.BigDecimal;

public class ItemImpl implements Item {

    private int itemId;
    private String itemName;
    private BigDecimal itemPrice;


    @Override
    public int getId() {
        // TODO Auto-generated method stub
        return itemId;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return itemName;
    }

    @Override
    public BigDecimal getPrice() {
        // TODO Auto-generated method stub
        return itemPrice;
    }

    @Override
    public void setId(int id) {
        // TODO Auto-generated method stub
        this.itemId=id;

    }

    @Override
    public void setName(String name) {
        // TODO Auto-generated method stub
        this.itemName=name;
    }

    @Override
    public void setPrice(BigDecimal price) {
        // TODO Auto-generated method stub
        this.itemPrice=price;
    }


}