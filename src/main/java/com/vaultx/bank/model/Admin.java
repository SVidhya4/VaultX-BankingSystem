package com.vaultx.bank.model;

public class Admin extends Employee {
    public Admin(String username, String password, String fullName, String empId, double salary) {
        super(username, password, fullName, "ADMIN", empId, salary);
    }
}