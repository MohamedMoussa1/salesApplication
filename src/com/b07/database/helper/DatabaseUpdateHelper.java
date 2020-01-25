package com.b07.database.helper;

import com.b07.database.DatabaseUpdater;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import com.b07.exceptions.*;

public class DatabaseUpdateHelper extends DatabaseUpdater {

  private static boolean nameCheck(String name) {

    return (!name.matches("[A-Z][a-z]* [A-Z][a-z]*") && !name.matches("[A-Z][a-z]*"));
  }

  private static boolean ageCheck(int age) {
    return (age < 0 || age > 100);
  }

  public static boolean updateRoleName(String name, int id) throws ConnectionCorruptionException {
    // TODO Implement this method as stated on the assignment sheet (Strawberry)
    // hint: You should be using these three lines in your final code

    if (nameCheck(name)) {
      System.out.println("Name entered is wrong. Return False");
      return false;

    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateRoleName(name, id, connection);
    try {

      connection.close();
    } catch (SQLException e) {
     throw new ConnectionCorruptionException();

    }
    return complete;

  }

  public static boolean updateUserName(String name, int userId) throws ConnectionCorruptionException {

    if (nameCheck(name)) {
      System.out.println("Name entered is incorrect");
      return false;
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserName(name, userId, connection);
    try {

      connection.close();
    } catch (SQLException e) {
     throw new ConnectionCorruptionException();
    }

    return complete;

  }

  public static boolean updateUserAge(int age, int userId) throws ConnectionCorruptionException {

    if (ageCheck(age)) {
      System.out.println("Invalid age entered");
      return false;
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserAge(age, userId, connection);
    try {

      connection.close();
    } catch (SQLException e) {
      throw new ConnectionCorruptionException();
    }
    return complete;

  }

  public static boolean updateUserAddress(String address, int userId) throws ConnectionCorruptionException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserAddress(address, userId, connection);
    try {
      connection.close();
    } catch (SQLException e) {
      throw new ConnectionCorruptionException();
    }
    return complete;

  }

  public static boolean updateUserRole(int roleId, int userId) throws ConnectionCorruptionException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserRole(roleId, userId, connection);
    try {
      connection.close();
    } catch (SQLException e) {
      throw new ConnectionCorruptionException();
    }
    return complete;

  }

  public static boolean updateItemName(String name, int itemId) throws ConnectionCorruptionException {
    if (nameCheck(name)) {
      System.out.println("Name entered is invalid. Return false");
      return false;
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateItemName(name, itemId, connection);

    try {
      connection.close();
    } catch (SQLException e) {
      throw new ConnectionCorruptionException();
    }
    return complete;

  }

  public static boolean updateItemPrice(BigDecimal price, int itemId) throws ConnectionCorruptionException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateItemPrice(price, itemId, connection);

    try {
      connection.close();
    } catch (SQLException e) {
      throw new ConnectionCorruptionException();
    }
    return complete;
  }

  public static boolean updateInventoryQuantity(int quantity, int itemId) throws LessThanZeroException, ConnectionCorruptionException {
    if (quantity < 0) {
      
      throw new LessThanZeroException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateInventoryQuantity(quantity, itemId, connection);

    try {
      connection.close();
    } catch (SQLException e) {
      throw new ConnectionCorruptionException();
      
    }
    return complete;
  }
}
