package com.b07.users;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import com.b07.exceptions.SaleAlreadyExistsException;
import com.b07.inventory.Item;
import com.b07.store.Sale;
import com.b07.store.SalesLog;
import com.b07.store.SalesLogImpl;

public class Admin extends User {
    public Admin(int id, String Name, int age, String address) {
        this.setId(id);
        this.setAge(age);
        this.setName(Name);
        this.setAddress(address);
    }

    public Admin(int id, String Name, int age, String address, boolean authenticated) {
        this.setId(id);
        this.setAge(age);
        this.setName(Name);
        this.setAddress(address);
        this.authenticated = authenticated;
    }

    public boolean promoteEmployee(Employee employee) {

        Admin newAdmin = null;
        newAdmin = new Admin(employee.getId(), employee.getName(), employee.getAge(), employee.getAddress());

        if (newAdmin != null) {
            employee = null;
            return true;
        } else {
            return false;
        }

    }

    private static HashMap<Item, Integer> printItems(Sale saleToPrint, HashMap<Item, Integer> itemToTotalNum) {
        HashMap<Item, Integer> itemMap = saleToPrint.getItemMap();
        Iterator mapIterator = itemMap.entrySet().iterator();

        while (mapIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) mapIterator.next();
            System.out.println(mapElement.getKey() + " : " + mapElement.getValue());
            itemToTotalNum.put((Item) mapElement.getKey(),
                    (Integer) mapElement.getValue() + itemToTotalNum.get(mapElement.getKey()));
        }
        return itemToTotalNum;
    }

   
    public static void viewBooks() throws SQLException, SaleAlreadyExistsException {
        SalesLog viewLog = new SalesLogImpl();
        List<Sale> salesLog=viewLog.getSalesLog();
        HashMap<Item,Integer> itemToTotalNum=new HashMap<>();
        BigDecimal totalSales=new BigDecimal(0);
        for(int i=0;i<salesLog.size();i++){
            Sale sale=salesLog.get(i);
            String customerName= sale.getUser().getName();
            System.out.println("Customer: "+customerName);
            System.out.println("Purchase Number: "+sale.getId());
            System.out.println("Total Purchase Price: "+sale.getTotalPrice());
            System.out.println("Itemized Breakdown");
            itemToTotalNum=Admin.printItems(sale,itemToTotalNum);
            totalSales=totalSales.add(sale.getTotalPrice());




        }
    
        for (Item item : itemToTotalNum.keySet()) { 
            String itemName = item.getName();  
            int quantity = itemToTotalNum.get(item); 
  
            System.out.println(itemName + " : " + quantity); 
        } 
   
        System.out.println("TOTAL SALES: "+ totalSales);
    }

}