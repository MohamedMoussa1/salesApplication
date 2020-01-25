package com.b07.users;

public enum Roles {
    ADMIN,
    EMPLOYEE,
    CUSTOMER;

public static boolean contains(String roleName) {

        for (Roles c : Roles.values()) {
            if (c.name().equals(roleName)) {
                return true;
            }
        }
    
        return false;
    }


}
