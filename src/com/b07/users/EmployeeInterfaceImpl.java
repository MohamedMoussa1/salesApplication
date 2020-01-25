package com.b07.users;

import java.sql.SQLException;

import javax.naming.InvalidNameException;

import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.inventory.*;
import com.b07.exceptions.*;

public class EmployeeInterfaceImpl implements EmployeeInterface {
    private Employee currentEmployee;
    private Inventory inventory;

    public EmployeeInterfaceImpl(Inventory inventoryToBeSet) {
        this.inventory = inventoryToBeSet;
    }

    public EmployeeInterfaceImpl(Employee employee, Inventory inventoryToBeSet)
            throws EmployeeNotAuthenticatedException {
        if (employee.authenticated) {
            this.inventory = inventoryToBeSet;
        } else {
            throw new EmployeeNotAuthenticatedException();
        }
    }

    @Override
    public void setCurrentEmployee(Employee employee) throws UserNotAuthenticatedException {
        if (employee.authenticated) {
            this.currentEmployee = employee;
        } else {
            throw new UserNotAuthenticatedException();
        }
    }


   


    @Override
    public int createEmployee(String name, int age, String address, String password)
            throws InvalidNameException, SQLException, DatabaseInsertException, InvalidAgeException,
            ConnectionCorruptionException {
        int newUserId = DatabaseInsertHelper.insertNewUser(name, age, address, password);
        DatabaseInsertHelper.insertUserRole(newUserId, 2);
        return newUserId;
    }

    public boolean hasCurrentEmployee() {
        return DatabaseSelectHelper.getUserRoleId(this.currentEmployee.getId()) == 2;
    }

    @Override
    public boolean restockInventory(Item item, int quantity)
            throws LessThanZeroException, ConnectionCorruptionException {

        if (DatabaseUpdateHelper.updateInventoryQuantity(quantity, item.getId())) {
            return true;
        } else {
            return false;
        }
    }

}
