package com.b07.database.helper;

import com.b07.database.DatabaseSelector;
import com.b07.exceptions.ConnectionCorruptionException;
import com.b07.exceptions.SaleAlreadyExistsException;
import com.b07.inventory.Inventory;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.Item;
import com.b07.inventory.ItemImpl;
import com.b07.store.Sale;
import com.b07.store.SaleImple;
import com.b07.store.SalesLog;
import com.b07.store.SalesLogImpl;
import com.b07.users.Customer;

import com.b07.users.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/*
 * TODO: Complete the below methods to be able to get information out of the database.
 * TODO: The given code is there to aide you in building your methods.  You don't have
 * TODO: to keep the exact code that is given (for example, DELETE the System.out.println())
 * TODO: and decide how to handle the possible exceptions
 */
public class DatabaseSelectHelper extends DatabaseSelector {
  public static List<Integer> getRoleIds() throws SQLException {

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    List<Integer> ids = new ArrayList<>();
    ResultSet results;
    try {

      results = DatabaseSelector.getRoles(connection);
    } catch (SQLException e1) {
      // cannot read from table exception
      return null;
    }
    while (results.next()) {
      try {
        ids.add(results.getInt("ID"));

      } catch (SQLException e2) {
        // cannot get from database
        continue;
      }
    }
    try {
      results.close();
      connection.close();
    } catch (SQLException e) {
      System.out.println("Couldn't get roleIds from the database.Returning null");
      return null;

    }
    return ids;

  }

  public static String getRoleName(int roleId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {

      String role = DatabaseSelector.getRole(roleId, connection);
      connection.close();
      return role;
    } catch (SQLException e1) {
      System.out.println("Cannot find the specified roleId. Returning null");
      return null;
    }

  }

  public static int getUserRoleId(int userId) {
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      int roleId = DatabaseSelector.getUserRole(userId, connection);
      connection.close();
      return roleId;
    } catch (SQLException e) {
      System.out.println("Couldn't get UserRoleId.Returning -1");
      return -1;
    }
  }

  /**
   * Returns the list of UserIds that have the roleId. Returns NULL if can't find
   * the specified roleId
   * 
   * @param roleId
   * @return List<Integer> userIds
   */
  public static List<Integer> getUsersByRole(int roleId) {
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelector.getUsersByRole(roleId, connection);
      List<Integer> userIds = new ArrayList<>();
      while (results.next()) {
        userIds.add(results.getInt("USERID"));
      }
      results.close();
      connection.close();
      return userIds;
    } catch (SQLException e) {
      System.out.println("Can't find the specified roleId. Return Null");
      return null;
    }
  }

  public static List<User> getUsersDetails() throws SQLException {
    ResultSet results;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    try {
      results = DatabaseSelector.getUsersDetails(connection);
    } catch (SQLException e) {
      System.out.println("Error in fetching the details. Returning null");
      return null;
    }
    List<User> users = new ArrayList<>();
    User newUser = new Customer(-1, "", 2, "");
    if (newUser != null) {
      while (results.next()) {
        try {
          newUser.setAddress(results.getString("ADDRESS"));
          newUser.setId(results.getInt("ID"));
          newUser.setAge(results.getInt("AGE"));
          newUser.setName(results.getString("NAME"));
          users.add((User) newUser);
        } catch (SQLException e) {
          continue;
        }
      }
    }
    try {
      results.close();
      connection.close();
      newUser = null;
      return users;
    } catch (SQLException e) {
      System.out.println("Connection corrupted");
      return null;

    }
  }

  public static User getUserDetails(int userId) throws SQLException, ConnectionCorruptionException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results;
    User reqUser = new Customer(-1, "", 2, "");
    try {
      results = DatabaseSelector.getUserDetails(userId, connection);
    } catch (SQLException e1) {
      System.out.println("Cannot fetch details from the table. Returning null");
      return null;
    }
    while (results.next()) {
      try {
        if (userId == results.getInt("ID")) {
          reqUser.setId(results.getInt("ID"));
          reqUser.setName(results.getString("NAME"));
          reqUser.setAge(results.getInt("AGE"));
          reqUser.setAddress(results.getString("ADDRESS"));
        }
      } catch (SQLException e2) {
        continue;
      }
    }
    try {
      results.close();
      connection.close();
      return (User) reqUser;
    } catch (SQLException e) {
      throw new ConnectionCorruptionException();
    }
  }

  public static String getPassword(int userId) throws ConnectionCorruptionException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    String password;
    try {
      password = DatabaseSelector.getPassword(userId, connection);
    } catch (SQLException e1) {
      System.out.println("Cannot retrieve password from the database. Return null");
      return null;
    }
    try {
      connection.close();
    } catch (SQLException e1) {
      throw new ConnectionCorruptionException();
    }
    return password;
  }

  public static List<Item> getAllItems() throws SQLException, ConnectionCorruptionException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results;
    try {
      results = DatabaseSelector.getAllItems(connection);
    } catch (SQLException e1) {
      System.out.println("Cannot retrieve items from database.Returning null");
      return null;
    }
    List<Item> items = new ArrayList<>();
    Item newItem = new ItemImpl();
    while (results.next()) {
      try {
        newItem.setId(results.getInt("ID"));
        newItem.setName(results.getString("NAME"));
        newItem.setPrice(new BigDecimal(results.getString("PRICE")));
        items.add(newItem);
      } catch (SQLException e2) {
        System.out.println("Cannot retrieve item's detail from the database. Return null");
        return null;
      }
    }
    try {
      results.close();
      connection.close();
    } catch (SQLException e3) {
      throw new ConnectionCorruptionException();
    }
    newItem = null;
    return items;
  }

  public static Item getItem(int itemId) throws SQLException, ConnectionCorruptionException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results;
    Item reqItem = new ItemImpl();
    try {
      results = DatabaseSelector.getItem(itemId, connection);
    } catch (SQLException e1) {
      System.out.println("Invalid ItemId.Return null");
      return null;
    }
    while (results.next()) {
      try {
        if (results.getInt("ID") == itemId) {
          reqItem.setId(results.getInt("ID"));
          reqItem.setName(results.getString("NAME"));
          reqItem.setPrice(new BigDecimal(results.getString("PRICE")));
        }
      } catch (SQLException e2) {
        continue;
      }
    }
    try {
      results.close();
      connection.close();
    } catch (SQLException e3) {
      throw new ConnectionCorruptionException();
    }
    return reqItem;
  }

  public static Inventory getInventory() throws SQLException, ConnectionCorruptionException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results;
    Inventory inventory = new InventoryImpl();
    Item item = new ItemImpl();
    int quantity;
    HashMap<Item, Integer> itemMap = new HashMap<Item, Integer>();
    try {
      results = DatabaseSelector.getInventory(connection);
    } catch (SQLException e1) {
      System.out.println("Cannot get inventory details from database. Returning null ");
      return null;
    }
    while (results.next()) {
      try {
        quantity = Integer.parseInt(results.getString("QUANTITY"));
        item = DatabaseSelectHelper.getItem(results.getInt("ITEMID"));

      } catch (SQLException e2) {
        continue;
      }
      itemMap.put(item, quantity);
      inventory.setItemMap(itemMap);
    }
    try {
      results.close();
      connection.close();
    } catch (SQLException e3) {
      throw new ConnectionCorruptionException();
    }
    return inventory;
  }

  public static int getInventoryQuantity(int itemId) throws ConnectionCorruptionException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int quantity;
    try {
      quantity = DatabaseSelector.getInventoryQuantity(itemId, connection);
    } catch (SQLException e1) {
      System.out.println("Cannot retrieve information from the database.Returning -1");
      return -1;
    }
    try {
      connection.close();
    } catch (SQLException e2) {
      throw new ConnectionCorruptionException();
    }

    return quantity;
  }

  public static SalesLog getSales() throws SQLException, SaleAlreadyExistsException, ConnectionCorruptionException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results;
    SalesLog salesLog = new SalesLogImpl();
    Sale sale = new SaleImple();
    try {
      results = DatabaseSelector.getSales(connection);
    } catch (SQLException e3) {
      System.out.println("Cannot retrieve sales' information from the database.Returning null");
      return null;
    }
    while (results.next()) {
      try {
        sale.setId(results.getInt("ID"));
        sale.setUser(DatabaseSelectHelper.getUserDetails(results.getInt("USERID")));
        sale.setTotalPrice(new BigDecimal(results.getString("TOTALPRICE")));
      } catch (SQLException e1) {
        continue;
      }
      salesLog.addSales(sale.getId());
    }
    try{
    results.close();
    connection.close();
    }catch(SQLException e2){
      throw new ConnectionCorruptionException();

    }
    
    return salesLog;
  }

  public static Sale getSaleById(int saleId) throws ConnectionCorruptionException,SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results;
    Sale sale = new SaleImple();
    User user;
    results = null;
    try {
      results = DatabaseSelector.getSaleById(saleId, connection);
    } catch (SQLException e1) {
      System.out.println("Cannot retrieve Sale information from the database.Returning null");
    }
    while (results.next()) {
      try {
        if (results != null) {
          sale.setId(results.getInt("ID"));
          user = getUserDetails(results.getInt("USERID"));
          sale.setUser(user);
          sale.setPrice(new BigDecimal(results.getString("TOTALPRICE")));
        }
      } catch (SQLException e2) {
        continue;
      }
    }
    try{
    results.close();
    connection.close();
    }catch(SQLException e3){
      throw new ConnectionCorruptionException();

    }
    return null;
  }

  public static List<Sale> getSalesToUser(int userId) throws SQLException, ConnectionCorruptionException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results;
    List<Sale> sales = new ArrayList<>();
    Sale sale = new SaleImple();
    User userInSale = new Customer(0, "", 8, "");
    try {
      results = DatabaseSelectHelper.getSalesToUser(userId, connection);

    } catch (SQLException e1) {
      // Using selectDatabaseSelect function exception
      return null;
    }
    while (results.next()) {
      try {
        sale.setId(results.getInt("ID"));
        userInSale = DatabaseSelectHelper.getUserDetails(results.getInt("USERID"));

        sale.setPrice(new BigDecimal(results.getString("TOTALPRICE")));
      } catch (SQLException e2) {
        continue;
      }
      sale.setUser(userInSale);
      sales.add(sale);
    }
    try {
      results.close();
      connection.close();
    } catch (SQLException e3) {
      throw new ConnectionCorruptionException();
     
    }
    return sales;
  }

  public static Sale getItemizedSaleById(int saleId) throws SQLException, ConnectionCorruptionException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results;
    Sale reqSale = new SaleImple();
    Item reqItem = new ItemImpl();
    HashMap<Item, Integer> saleItemMap = new HashMap<>();
    int quantity = 0;
    try {
      results = DatabaseSelector.getItemizedSaleById(saleId, connection);
    } catch (SQLException e1) {
      // cannot read from table exception
      return null;
    }
    while (results.next()) {
      try {
        if (saleId == results.getInt("SALEID")) {
          reqItem = DatabaseSelectHelper.getItem(results.getInt("ITEMID"));
          quantity = results.getInt("QUANTITY");
        }

      } catch (SQLException e2) {
        continue;
      }
      saleItemMap.put(reqItem, quantity);
      reqSale.setItemMap(saleItemMap);
      reqSale.setId(saleId);

    }
    try {
      results.close();
      connection.close();
    } catch (SQLException e3) {
      throw new ConnectionCorruptionException();
      
    }
    return reqSale;
  }

  public static SalesLog getItemizedSales()
      throws ConnectionCorruptionException, SQLException, SaleAlreadyExistsException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results;
    SalesLog salesLog = new SalesLogImpl();
    Sale sale=new SaleImple();
    try {
      results = DatabaseSelector.getItemizedSales(connection);
    } catch (SQLException e1) {
      // cannot read from table exception
      return null;
    }
    while (results.next()) {
      try {
        sale.setId(results.getInt("SALEID"));
        Item item=new ItemImpl();
        
        item.setId(results.getInt("ITEMID"));
        int quantity=(results.getInt("QUANTITY"));
        HashMap<Item,Integer> newItemMap=new HashMap<>();
        newItemMap.put(item,quantity);
        sale.setItemMap(newItemMap);
        salesLog.addSales(sale.getId());
      } catch (SQLException e2) {
        continue;
      }
    }
    try {
      results.close();
      connection.close();
    } catch (SQLException e3) {
      throw new ConnectionCorruptionException();
      

    }
    return salesLog;
  }
}
