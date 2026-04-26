package com.vaultx.bank.factory;

import com.vaultx.bank.model.*;
import java.sql.*;

public class UserFactory {
    public User createUser(ResultSet rs) throws SQLException {
        String role = rs.getString("role").toUpperCase();

        return switch (role) {
            case "CUSTOMER" -> new Customer(
                    rs.getString("username"), rs.getString("password"),
                    rs.getString("fullName"), rs.getString("phoneNumber"));

            case "ADMIN" -> new Admin(
                    rs.getString("username"), rs.getString("password"),
                    rs.getString("fullName"), rs.getString("employeeId"), rs.getDouble("salary"));

            case "MANAGER" -> new Manager(
                    rs.getString("username"), rs.getString("password"),
                    rs.getString("fullName"), rs.getString("employeeId"), rs.getDouble("salary"));

            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
    }
}