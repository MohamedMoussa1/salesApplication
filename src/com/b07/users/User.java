package com.b07.users;

import com.b07.security.PasswordHelpers;
import com.b07.database.helper.*;
import com.b07.exceptions.ConnectionCorruptionException;

public abstract class User {
  // TODO: Complete this class based on UML provided on the assignment sheet.

  private int id;
  private int age;
  private int roleId;
  private String name;
  private String address;
  protected boolean authenticated;

  public int getId() {

    return id;
  }

  public void setId(int changeId) {

    this.id = changeId;
  }

  public String getName() {

    return name;
  }

  public void setName(String name) {
    this.name = name;

  }

  public int getAge() {

    return age;
  }

  public void setAge(int age) {
    this.age = age;

  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String Address) {
    this.address = Address;

  }

  public int getRoleId() {
    return roleId;
  }

  public final boolean authenticate(String password) throws ConnectionCorruptionException {

    String storedPass= DatabaseSelectHelper.getPassword(this.id);
    this.authenticated=PasswordHelpers.comparePassword(storedPass, password);    
    return this.authenticated;
  }
  
  
}
