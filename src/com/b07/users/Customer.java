package com.b07.users;

public class Customer extends User{

    public Customer(int id,String name ,int age,String address){
        this.setId(id);
        this.setName(name);
        this.setAge(age);
        this.setAddress(address);
 }   
    public Customer(int id,String name ,int age,String address,boolean authenticated){
        this.setId(id);
        this.setName(name);
        this.setAge(age);
        this.setAddress(address);
        this.authenticated=authenticated;
    }
}