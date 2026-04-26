package com.vaultx.bank.dao;

import com.vaultx.bank.config.DBConnection;
import com.vaultx.bank.model.Transaction;
import java.sql.*;

public class TransactionDAO {

    public boolean performAtomicTransfer(int senderAccId, String senderAcc, String receiverAcc, double amount) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // START ATOMIC BLOCK

            // 1. Deduct from Sender
            String deductSql = "UPDATE accounts SET balance = balance - ? WHERE accNumber = ?";
            PreparedStatement p1 = conn.prepareStatement(deductSql);
            p1.setDouble(1, amount);
            p1.setString(2, senderAcc);
            p1.executeUpdate();

            // 2. Add to Receiver
            String addSql = "UPDATE accounts SET balance = balance + ? WHERE accNumber = ?";
            PreparedStatement p2 = conn.prepareStatement(addSql);
            p2.setDouble(1, amount);
            p2.setString(2, receiverAcc);
            p2.executeUpdate();

            // 3. Log the Transaction
            String logSql = "INSERT INTO transactions (accId, amount, txnType, description) VALUES (?, ?, ?, ?)";
            PreparedStatement p3 = conn.prepareStatement(logSql);
            p3.setInt(1, senderAccId);
            p3.setDouble(2, amount);
            p3.setString(3, "TRANSFER");
            p3.setString(4, "To: " + receiverAcc);
            p3.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return false;
        }
    }

    public java.util.List<com.vaultx.bank.model.Transaction> getHistoryByAccountId(int accId) {
        java.util.List<com.vaultx.bank.model.Transaction> history = new java.util.ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE accId = ? ORDER BY timestamp DESC";

        try (java.sql.Connection conn = com.vaultx.bank.config.DBConnection.getConnection();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accId);
            java.sql.ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                com.vaultx.bank.model.Transaction txn = new com.vaultx.bank.model.Transaction(
                        rs.getInt("accId"), rs.getDouble("amount"),
                        rs.getString("txnType"), rs.getString("description"));
                txn.setTimestamp(rs.getTimestamp("timestamp"));
                history.add(txn);
            }
        } catch (java.sql.SQLException e) { e.printStackTrace(); }
        return history;
    }
}