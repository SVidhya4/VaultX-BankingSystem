package com.vaultx.bank.model;

public abstract class Employee extends User {
    private String employeeId;
    private double salary;

    public Employee(String username, String password, String fullName, String role, String employeeId, double salary) {
        super(username, password, fullName, role);
        this.employeeId = employeeId;
        this.salary = salary;
    }

    public String getEmployeeId() { return employeeId; }
    public double getSalary() { return salary; }
}