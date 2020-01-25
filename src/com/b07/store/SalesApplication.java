package com.b07.store;

import java.sql.Connection;

import java.sql.SQLException;

import javax.naming.InvalidNameException;

import com.b07.database.helper.*;
import com.b07.inventory.Inventory;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.Item;
import com.b07.inventory.ItemTypes;

import com.b07.users.*;
import com.b07.exceptions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class SalesApplication {

  protected static void promoteEmployee(Admin oldAdmin)
      throws IOException, SQLException, ConnectionCorruptionException {
    String wish;
    String employeeId;
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("Enter Employee Id to promote to admin");
    employeeId = bufferedReader.readLine();
    System.out.println("Do you wish to promote the employee with Id" + employeeId + "to administrator");
    wish = bufferedReader.readLine();
    if (wish.contentEquals("Y") || wish.contentEquals("y")) {
      Employee newAdmin = (Employee) DatabaseSelectHelper.getUserDetails(Integer.valueOf(employeeId));
      if (oldAdmin.promoteEmployee(newAdmin)) {
        System.out.println("Employee promoted successfully");
      } else {
        System.out.println("Employee couldn't be promoted");

      }

    }
    return;
  }

  protected static void adminOptions(Admin currentAdmin)
      throws IOException, SQLException, ConnectionCorruptionException, SaleAlreadyExistsException {
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
    String wish;

    System.out.println("1. Promote Employee");
    System.out.println("2. View Books");
    System.out.println("Press any other key to exit");
    wish = inputReader.readLine();

    while (wish.contentEquals("1") || wish.contentEquals("2")) {
      if (currentAdmin != null) {
        if (wish.contentEquals("1")) {
          SalesApplication.promoteEmployee(currentAdmin);
        } else if (wish.contentEquals("2")) {
            Admin.viewBooks();

        } else {
          break;
        }

      }
      return;

    }
  }

  protected static void callEmployeeInterface()
      throws IOException, ConnectionCorruptionException, LessThanZeroException, NumberFormatException, SQLException,
      InvalidNameException, DatabaseInsertException, InvalidAgeException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String inputString;
    while (true) {
      System.out.println("Enter Id:");
      inputString = bufferedReader.readLine();
      int employeeId = Integer.valueOf(inputString);
      if (DatabaseSelectHelper.getUserRoleId(employeeId) != 2) {
        System.out.println("Invalid Employee Id. Session terminating");
        break;
      } else {
        System.out.println("Enter Password:");
        inputString = bufferedReader.readLine();
        Employee authentEmployee = new Employee(employeeId, "name", 50, "address");
        if (authentEmployee.authenticate(inputString)) {
          Inventory baseInventory = new InventoryImpl();
          EmployeeInterface employeeInterfaceObject = new EmployeeInterfaceImpl(baseInventory);
          while (true) {
            System.out.println("1. Authenticate new Employee");
            System.out.println("2. Make new User");
            System.out.println("3. Make new account");
            System.out.println("4. Make new Employee");
            System.out.println("5. Restock Inventory");
            System.out.println("6. Exit");
            System.out.println("Enter your choice[1:6]");

            String wish;
            wish = bufferedReader.readLine();
            if (wish.contentEquals("1")) {
              System.out.println("Enter Id of employee to be authenticated");
              String employId = bufferedReader.readLine();
              Employee employeeToBeAuthenticated = (Employee) DatabaseSelectHelper
                  .getUserDetails(Integer.valueOf(employId));
              System.out.println("These are the details of the employee with Id" + employId);
              System.out.println("ID:" + employeeToBeAuthenticated.getId());
              System.out.println("Name:" + employeeToBeAuthenticated.getName());
              System.out.println("Age:" + employeeToBeAuthenticated.getAge());
              System.out.println("Address:" + employeeToBeAuthenticated.getAddress());
              System.out.println("Do you want to authenticate this employee? Y|N");
              String wantToAuthenticate;
              wantToAuthenticate = bufferedReader.readLine();
              if (wantToAuthenticate.contentEquals("Y") || wantToAuthenticate.contentEquals("y")) {
                String employeePassword = bufferedReader.readLine();
                if (employeeToBeAuthenticated.authenticate(employeePassword)) {
                  System.out.println("Employee has been authenticated");
                }
              }

            } else if (wish.contentEquals("2")) {

              String name;
              String password;
              String age;
              String address;

              System.out.println("Enter name of user");
              name = bufferedReader.readLine();

              System.out.println("Enter age of user");
              age = bufferedReader.readLine();

              System.out.println("Enter address of user");
              address = bufferedReader.readLine();

              System.out.println("Enter password of user");
              password = bufferedReader.readLine();

              int newUserId = DatabaseInsertHelper.insertNewUser(name, Integer.valueOf(age), address, password);
              System.out.println("User Id of new user is:" + newUserId);

            } else if (wish.contentEquals("3")) {
              String name;
              String password;
              String age;
              String address;
              String roleId;
              System.out.println("Enter name of user");
              name = bufferedReader.readLine();

              System.out.println("Enter age of user");
              age = bufferedReader.readLine();

              System.out.println("Enter address of user");
              address = bufferedReader.readLine();

              System.out.println("Enter password of user");
              password = bufferedReader.readLine();
              System.out.println("Enter roleId of user");
              roleId = bufferedReader.readLine();

              int userId = DatabaseInsertHelper.insertNewUser(name, Integer.valueOf(age), address, password);
              DatabaseInsertHelper.insertUserRole(userId, Integer.valueOf(roleId));
              System.out.println("User Id of new user is:" + userId);

            } else if (wish.contentEquals("4")) {

              String name;
              String password;
              String age;
              String address;
              System.out.println("Enter employee's name");
              name = bufferedReader.readLine();

              System.out.println("Enter employee's age");
              age = bufferedReader.readLine();

              System.out.println("Enter employee's address");
              address = bufferedReader.readLine();

              System.out.println("Enter employee's password");
              password = bufferedReader.readLine();

              int employId = employeeInterfaceObject.createEmployee(name, Integer.valueOf(age), address, password);
              System.out.println("Id of new employee is:" + employId);

            } else if (wish.contentEquals("5")) {
              String itemId;
              String itemQuantity;
              System.out.println("Enter ItemId to be restocked:");
              itemId = bufferedReader.readLine();
              System.out.println("Enter quantity of the item in stock:");
              itemQuantity = bufferedReader.readLine();
              Item restockingItem = DatabaseSelectHelper.getItem(Integer.valueOf(itemId));
              int quantity = Integer.valueOf(itemQuantity);
              employeeInterfaceObject.restockInventory(restockingItem, quantity);
            } else if (wish.contentEquals("6")) {
              break;
            }

          }

        } else {
          System.out.println("Invalid password. Session terminating");
          break;
        }

      }

    }
  }

  protected static void callShoppinCart() throws IOException, CustomerNotLoggedInException, SQLException,
      ConnectionCorruptionException, LessThanZeroException {
    String inputString;
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    while (true) {
      System.out.println("Enter id:");
      inputString = bufferedReader.readLine();
      int customerId = Integer.valueOf(inputString);
      if (DatabaseSelectHelper.getUserRoleId(customerId) != 3) {
        System.out.println("Invalid Login Id. Session terminating");
        break;
      } else {
        System.out.println("Enter Password:");
        inputString = bufferedReader.readLine();
        Customer authentCustomer = new Customer(customerId, "name", 3, "address");
        if (authentCustomer.authenticate(inputString)) {
          while (true) {
            ShoppingCart newCart = new ShoppingCartImpl((Customer) DatabaseSelectHelper.getUserDetails(customerId));
            System.out.println("1.  List current items in cart");
            System.out.println("2.  Add a quantity of an item to the cart");
            System.out.println("3.  Check total price of items in the cart");
            System.out.println("4. Remove a quantity of an item from the cart");
            System.out.println("5. Check out");
            System.out.println("6. Exit");
            System.out.println("Enter your choice[1:6]");

            inputString = bufferedReader.readLine();
            if (inputString.contentEquals("1")) {
              System.out.println("You have the following items in your cart currently");

              for (Item item : newCart.getItems().keySet()) {
                System.out.println(item.getName() + " : " + newCart.getItems().get(item));
              }
            } else if (inputString.contentEquals("2")) {
              String inputId;
              String inputAmount;
              System.out.println("Enter the item ID:");
              inputId = bufferedReader.readLine();
              System.out.println("Enter the amount to be added:");
              inputAmount = bufferedReader.readLine();
              newCart.addItemQuantity(Integer.valueOf(inputId), Integer.valueOf(inputAmount));

            } else if (inputString.contentEquals("3")) {
              newCart.getTotal();
            } else if (inputString.contentEquals("4")) {
              String inputId;
              String inputAmount;
              System.out.println("Enter the item ID:");
              inputId = bufferedReader.readLine();
              System.out.println("Enter the amount to be deleted:");
              inputAmount = bufferedReader.readLine();
              newCart.deleteItemQuantity(Integer.valueOf(inputId), Integer.valueOf(inputAmount));

            } else if (inputString.contentEquals("5")) {
              String wish;
              wish = bufferedReader.readLine();
              System.out.println("Do you want to continue checking out?Y|N");
              if (wish.contentEquals("Y") || wish.contentEquals("y")) {
                try {
                  newCart.checkOutCustomer();
                } catch (CustomerNotLoggedInException e1) {
                  System.out.println("Login first to continue checking out");
                }

              } else {
                continue;
              }
            } else if (inputString.contentEquals("6")) {
              break;
            }
          }
        }

      }
    }
  }

  /**
   * This is the main method to run your entire program! Follow the "Pulling it
   * together" instructions to finish this off.
   * 
   * @param argv unused.
   * 
   */
  public static void main(String[] argv) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String inputString = "";

    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    if (connection == null) {
      System.out.print("NOOO");
    }
    try {
      while (true) {
        int count=0;
        if(count==0){
        System.out.println("Enter -1 to create a database.First time use only");
         } System.out.println("Enter 1 to go to Administrator mode");
        System.out.println("Enter 0 to exit the session");
        System.out.println("Enter anything else to go to customer or employee view");
        inputString = reader.readLine();
        if (inputString.contentEquals("0")) {
          break;
        } else if (inputString.contentEquals("-1")&& count==0) {
          count++;
          DatabaseDriverExtender.initialize(connection);

          DatabaseInsertHelper.insertNewUser("Dave", 22, "176 Michigan Avenue", "Batman123");
          DatabaseInsertHelper.insertNewUser("David", 24, "224 Michael Avenue", "IronManRoxx");
          DatabaseInsertHelper.insertNewUser("Bruce", 32, "224 Ashton Drive", "CaptainAmerica");

          DatabaseInsertHelper.insertRole("ADMIN");
          DatabaseInsertHelper.insertRole("EMPLOYEE");
          DatabaseInsertHelper.insertRole("CUSTOMER");

          DatabaseInsertHelper.insertUserRole(1, 1);
          DatabaseInsertHelper.insertUserRole(2, 2);
          DatabaseInsertHelper.insertUserRole(3, 3);

          DatabaseInsertHelper.insertItem(ItemTypes.FISHING_ROD.toString(), new BigDecimal(12));
          DatabaseInsertHelper.insertItem(ItemTypes.HOCKEY_STICK.toString(), new BigDecimal(15));
          DatabaseInsertHelper.insertItem(ItemTypes.SKATES.toString(), new BigDecimal(10));
          DatabaseInsertHelper.insertItem(ItemTypes.RUNNING_SHOES.toString(), new BigDecimal(200));
          DatabaseInsertHelper.insertItem(ItemTypes.PROTEIN_BAR.toString(), new BigDecimal(2));

        } else if (inputString.contentEquals("1")) {
          System.out.println("Welcome to the administrator page");
          System.out.println("Enter your login details:");
          System.out.println("Enter your ID:");
          inputString = reader.readLine();
          int idToCheck = -1;
          try {
            idToCheck = (int) Integer.valueOf(inputString);
            DatabaseSelectHelper.getUserDetails(idToCheck);
          } catch (SQLException | NumberFormatException e1) {
            System.out.println("Invalid ID enetered. Try again");
            inputString = reader.readLine();
            idToCheck = (int) Integer.valueOf(inputString);

          } catch (ConnectionCorruptionException e2) {
            // TODO:Handle connectioncorruptionexception
          }

          User authenticatorObject = new Admin(2, "Name", 22, "address");
          do {
            System.out.println("Enter your password:");
            inputString = reader.readLine();
          } while (!authenticatorObject.authenticate(inputString));

          User userDataObj = DatabaseSelectHelper.getUserDetails(idToCheck);
          Admin currentAdmin = new Admin(userDataObj.getId(), userDataObj.getName(), userDataObj.getAge(),
              userDataObj.getAddress());
          SalesApplication.adminOptions(currentAdmin);

        } else {
          System.out.println("1. Employee Login");
          System.out.println("2. Customer Login");
          System.out.println("0. Exit");
          System.out.println("Enter the option number");
          inputString = reader.readLine();
          if (inputString.contentEquals("1")) {
            SalesApplication.callEmployeeInterface();
            break;
          } else if (inputString.contentEquals("2")) {
            SalesApplication.callShoppinCart();
            break;

          } else if (inputString.contentEquals("0")) {
            break;
          }

        }

      }
      // TODO Check what is in argv
      // If it is -1
      /*
       * TODO This is for the first run only! Add this code:
       * 
       * Then add code to create your first account, an administrator with a password
       * Once this is done, create an employee account as well.
       * 
       */
      // If it is 1
      /*
       * TODO In admin mode, the user must first login with a valid admin account This
       * will allow the user to promote employees to admins. Currently, this is all an
       * admin can do.
       */
      // If anything else - including nothing
      /*
       * TODO Create a context menu, where the user is prompted with: 1 - Employee
       * Login 2 - Customer Login 0 - Exit Enter Selection:
       */
      // If the user entered 1
      /*
       * TODO Create a context menu for the Employee interface Prompt the employee for
       * their id and password Attempt to authenticate them. If the Id is not that of
       * an employee or password is incorrect, end the session If the Id is an
       * employee, and the password is correct, create an EmployeeInterface object
       * then give them the following options: 1. authenticate new employee 2. Make
       * new User 3. Make new account 4. Make new Employee 5. Restock Inventory 6.
       * Exit
       * 
       * Continue to loop through as appropriate, ending once you get an exit code (9)
       */
      // If the user entered 2
      /*
       * TODO create a context menu for the customer Shopping cart Prompt the customer
       * for their id and password Attempt to authenticate them If the authentication
       * fails or they are not a customer, repeat If they get authenticated and are a
       * customer, give them this menu: 1. List current items in cart 2. Add a
       * quantity of an item to the cart 3. Check total price of items in the cart 4.
       * Remove a quantity of an item from the cart 5. check out 6. Exit
       * 
       * When checking out, be sure to display the customers total, and ask them if
       * they wish to continue shopping for a new order
       * 
       * For each of these, loop through and continue prompting for the information
       * needed Continue showing the context menu, until the user gives a 6 as input.
       */
      // If the user entered 0
      /*
       * TODO Exit condition
       */
      // If the user entered anything else:
      /*
       * TODO Re-prompt the user
       */

    } catch (Exception e) {
      // TODO Improve this!
      System.out.println("Something went wrong :(");
    } finally {
      try {
        connection.close();
      } catch (Exception e) {
        System.out.println("Looks like it was closed already :)");
      }

    }

  }
}
