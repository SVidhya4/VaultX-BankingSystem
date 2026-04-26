package com.vaultx.bank.model;

public abstract class Account {
    private int accId;
    private int userId;
    private String accNumber;
    protected double balance;

    @Override
    public String toString() {
        return accNumber + " (Balance: ₹" + balance + ")";
    }

    public Account(int accId, int userId, String accNumber, double balance) {
        this.accId = accId;
        this.userId = userId;
        this.accNumber = accNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) this.balance += amount;
    }

    public abstract boolean withdraw(double amount);

    public double getBalance() { return balance; }
    public String getAccNumber() { return accNumber; }
    public int getAccId() { return accId; }
    public void setAccId(int accId) { this.accId = accId; }

    public void setBalance(double v) {
        this.balance = v;
    }

    public String getAccountNumber() {
        return accNumber;
    }

    public int getUserId() {
        return userId;
    }
}