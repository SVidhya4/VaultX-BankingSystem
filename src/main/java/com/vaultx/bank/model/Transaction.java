package com.vaultx.bank.model;
import java.sql.Timestamp;

public class Transaction {
    private int txnId;
    private int accId;
    private double amount;
    private String txnType;
    private Timestamp timestamp;
    private String description;

    private String senderAccNum;
    private String receiverAccNum;
    private String receiverName;

    public Transaction(int accId, double amount, String txnType, String description) {
        this.accId = accId;
        this.amount = amount;
        this.txnType = txnType;
        this.description = description;
    }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String name) { this.receiverName = name; }

    public String getSenderAccNum() { return senderAccNum; }
    public void setSenderAccNum(String num) { this.senderAccNum = num; }


    public int getAccId(){ return accId;}
    public double getAmount() { return amount; }
    public String getTxnType() { return txnType; }
    public Timestamp getTimestamp() { return timestamp; }
    public String getDescription() { return description; }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}