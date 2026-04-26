package com.vaultx.bank.service;

import com.vaultx.bank.dao.AccountDAO;
import com.vaultx.bank.dao.TransactionDAO;
import com.vaultx.bank.model.Account;

public class BankService {
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    public BankService(AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }

    public String processTransfer(Account sender, String receiverAccNum, double amount, String pin) {
        if (amount <= 0) return "Invalid amount!";
        if (sender.getBalance() < amount) return "Insufficient funds!";
        if (sender.getAccountNumber().equals(receiverAccNum)) return "Cannot transfer to self!";

        if (!verifyPin(sender.getUserId(), pin)) return "Invalid Transaction PIN!";

        boolean success = transactionDAO.performAtomicTransfer(
                sender.getAccId(),
                sender.getAccountNumber(),
                receiverAccNum,
                amount
        );

        if (success) {
            sender.withdraw(amount);
            return "Transfer Successful! Reference: " + System.currentTimeMillis();
        }

        return "Transaction Failed. Please check the receiver's account number.";
    }

    public boolean verifyPin(int userId, String enteredPin) {
        //to be implemented
        return true;
    }
}