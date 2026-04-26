package com.vaultx.bank.dao;

import com.vaultx.bank.config.DBConnection;
import com.vaultx.bank.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public List<Account> getAccountsByUserId(int userId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE userId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("accId");
                String accNum = rs.getString("accNumber");
                double bal = rs.getDouble("balance");
                String type = rs.getString("accType");

                if (type.equalsIgnoreCase("SAVINGS")) {
                    accounts.add(new SavingsAccount(id, userId, accNum, bal));
                } else {
                    accounts.add(new CurrentAccount(id, userId, accNum, bal));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return accounts;
    }

    public boolean updateBalance(String accNumber, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE accNumber = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accNumber);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) { return false; }
    }

    public String getAccountHolderName(String accNumber) {
        String sql = "SELECT u.fullName FROM users u JOIN accounts a ON u.userId = a.userId WHERE a.accNumber = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("fullName");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}