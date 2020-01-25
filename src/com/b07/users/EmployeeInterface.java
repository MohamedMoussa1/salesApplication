package com.b07.users;

import java.sql.SQLException;

import javax.naming.InvalidNameException;

import com.b07.exceptions.*;
import com.b07.inventory.*;

public interface EmployeeInterface {
    public void setCurrentEmployee(Employee employee) throws UserNotAuthenticatedException;

    public int createEmployee(String name,int age,String address,String password) throws InvalidNameException, SQLException, DatabaseInsertException, InvalidAgeException, ConnectionCorruptionException;

    public boolean hasCurrentEmployee();

    public boolean restockInventory(Item item, int quantity) throws LessThanZeroException, ConnectionCorruptionException;

}