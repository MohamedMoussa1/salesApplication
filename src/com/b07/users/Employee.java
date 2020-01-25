package com.b07.users;

public class Employee extends User{
    public Employee(int id,String name,int age,String address){
        this.setAge(age);
        this.setId(id);
        this.setAddress(address);
        this.setName(name);
    }

    public Employee(int id,String name,int age,String address,boolean authenticated){
        this.setAge(age);
        this.setId(id);
        this.setAddress(address);
        this.setName(name);
        this.authenticated=authenticated;
    }

}