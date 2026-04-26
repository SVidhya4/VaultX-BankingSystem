package com.vaultx.bank.model;

/**
 * Abstract base class for all identities in VaultX.
 * Follows the Open/Closed Principle.
 */
public abstract class User {
    private int userId;
    private String username;
    private String password;
    private String transactionPin;
    private String fullName;
    private String role;

    public User(String username, String password, String fullName, String role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    // Standard Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getRole() { return role; }
    public String getTransactionPin() { return transactionPin; }
    public void setTransactionPin(String pin) { this.transactionPin = pin; }
}