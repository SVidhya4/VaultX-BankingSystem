package com.vaultx.bank.model;

public class Manager extends Employee {
    public Manager(String username, String password, String fullName, String empId, double salary) {
        super(username, password, fullName, "MANAGER", empId, salary);
    }
}