package com.vaultx.bank.model;

public class CurrentAccount extends Account {
    private double overdraftLimit = 5000.0;

    public CurrentAccount(int accId, int userId, String accNumber, double balance) {
        super(userId, userId, accNumber, balance);
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance + overdraftLimit >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}