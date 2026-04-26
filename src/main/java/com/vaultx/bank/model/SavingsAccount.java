package com.vaultx.bank.model;

public class SavingsAccount extends Account {
    private static final double MIN_BALANCE = 1000.0;
    private double interestRate = 0.04;

    public SavingsAccount(int accId, int userId, String accNumber, double balance) {
        super(accId, userId, accNumber, balance);
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance - amount >= MIN_BALANCE) {
            balance -= amount;
            return true;
        }
        return false;
    }
}