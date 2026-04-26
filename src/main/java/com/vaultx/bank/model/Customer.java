package com.vaultx.bank.model;

public class Customer extends User {
    private String phoneNumber; // Customer-specific field

    public Customer(String username, String password, String fullName, String phoneNumber) {
        super(username, password, fullName, "CUSTOMER");
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() { return phoneNumber; }
}