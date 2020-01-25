package com.b07.database.helper;

import com.b07.database.DatabaseInserter;

import com.b07.exceptions.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InvalidNameException;
import javax.naming.NamingException;

public class DatabaseInsertHelper extends DatabaseInserter {

	private static boolean nameCheck(String name) {
		return (!(name.matches("[A-Z][a-z]*") || name.matches("[A-Z][a-z]*_[A-Z][a-z]*")));
	}

	// Format this
	public static int insertRole(String roleName) throws DatabaseInsertException,ConnectionCorruptionException{
		// TODO Implement this method as stated on the assignment sheet
		// hint: You should be using these three lines in your final code

		try {
			if (!roleName.matches("[A-Z]*") && !roleName.matches("[A-Z]*_[A-Z]*")) {
				throw new RoleNotValidException();
			}
			Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
			int roleId = DatabaseInserter.insertRole(roleName, connection);
			connection.close();
			return roleId;
		} catch (RoleNotValidException e3) {
			System.out.println("roleName should be in UpperCase.Returning -1 ");
			return -1;

		} catch (SQLException e1) {
			throw new ConnectionCorruptionException();

		} 

	}

	public static int insertNewUser(String name, int age, String address, String password)
			throws SQLException, DatabaseInsertException, InvalidNameException, InvalidAgeException,
			ConnectionCorruptionException {
		// TODO Implement this method as stated on the assignment sheet
		// hint: You should be using these three lines in your final code

		try {
			if (DatabaseInsertHelper.nameCheck(name)) {

				throw new InvalidNameException("Name isn't in right format");
			} else if (age < 0 || age > 100) {
				throw new InvalidAgeException();
			} else {
				Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
				int userId = DatabaseInserter.insertNewUser(name, age, address, password, connection);
				connection.close();
				return userId;
			}
		} catch (InvalidAgeException e1) {
			System.out.println("Age out of bounds.Returning -1");
			return -1;

		} catch (InvalidNameException e3) {
			System.out.println("Name is not in the right format.Returning -1");
			return -1;

		}  catch (SQLException e1) {
			throw new ConnectionCorruptionException();
		}

	}

	public static int insertUserRole(int userId, int roleId) throws DatabaseInsertException, ConnectionCorruptionException {
		// TODO Implement this method as stated on the assignment sheet
		// hint: You should be using these three lines in your final code
		// Connection dbconnection = DatabaseDriverHelper.connectOrCreateDataBase();
		try {
			Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
			int userRoleId = DatabaseInserter.insertUserRole(userId, roleId, connection);
			connection.close();
			return userRoleId;
		} catch (SQLException e1) {
			throw new ConnectionCorruptionException();
		} 

	}

	public static int insertItem(String name, BigDecimal price) throws ConnectionCorruptionException, DatabaseInsertException {
		// TODO Implement this method as stated on the assignment sheet
		// hint: You should be using these three lines in your final code
		try {
			if (name.length() > 64) {
				throw new NamingException("Name exceeds 64 characters");
			}
			Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
			int itemId = DatabaseInserter.insertItem(name, price, connection);
			connection.close();
			return itemId;
		} catch (NamingException e1) {
			System.out.println("Name not in the valid format.Returning -1");
			return -1;
		}  catch (SQLException e3) {
			throw new ConnectionCorruptionException();
		}

	}

	public static int insertInventory(int itemId, int quantity) throws DatabaseInsertException, SQLException, ConnectionCorruptionException {
		// TODO Implement this method as stated on the assignment sheet
		// hint: You should be using these three lines in your final code
		try {
			if (quantity < 0) {
				throw new QuantityInvalidException();
			} else {
				Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
				int inventoryId = DatabaseInserter.insertInventory(itemId, quantity, connection);
				connection.close();
				return inventoryId;
			}
		} catch (QuantityInvalidException e1) {
			System.out.println("Entered quantity is invalid(Less than zero).Returning -1");
		}  catch (SQLException e3) {
				throw new ConnectionCorruptionException();
		}return -1;
	}

	public static int insertSale(int userId, BigDecimal totalPrice) throws ConnectionCorruptionException { // How to
																											// check
																											// totalPrice
		// TODO Implement this method as stated on the assignment sheet
		// hint: You should be using these three lines in your final code

		Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
		try {
			int saleId = DatabaseInserter.insertSale(userId, totalPrice, connection);
			connection.close();
			return saleId;

		} catch (DatabaseInsertException e1) {
			System.out.println("Couldn't register the sale.Return -1");
			return -1;

		} catch (SQLException e2) {
			throw new ConnectionCorruptionException();
		}

	}

	public static int insertItemizedSale(int saleId, int itemId, int quantity) throws ConnectionCorruptionException,DatabaseInsertException {
		// TODO Implement this method as stated on the assignment sheet
		// hint: You should be using these three lines in your final code
		try {
			if (quantity < 0) {
				throw new QuantityInvalidException();
			}

			Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
			int itemizedId = DatabaseInserter.insertItemizedSale(saleId, itemId, quantity, connection);
			connection.close();
			return itemizedId;
		} catch (QuantityInvalidException e1) {
			System.out.println("Entered Quantity is not valid(Less than zero).Returning -1");
			return -1;

		}  catch (SQLException e3) {
			throw new ConnectionCorruptionException();
		}

	}

}
