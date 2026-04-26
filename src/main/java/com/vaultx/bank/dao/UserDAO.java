package com.vaultx.bank.dao;

import com.vaultx.bank.config.DBConnection;
import com.vaultx.bank.factory.UserFactory;
import com.vaultx.bank.model.*;
import java.sql.*;

public class UserDAO {
    private final UserFactory factory;

    public UserDAO(UserFactory factory) {
        this.factory = factory;
    }

    public User loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = factory.createUser(rs);
                user.setUserId(rs.getInt("userId"));
                return user;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}