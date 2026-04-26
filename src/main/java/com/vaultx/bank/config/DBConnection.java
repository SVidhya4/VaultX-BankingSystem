package com.vaultx.bank.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    /**
     * Retrieves the current active connection or creates a new one if none exists.
     * Uses JDBC Driver for MySQL connectivity.
     * * @return java.sql.Connection The established database connection.
     * @throws SQLException If database access fails or credentials are incorrect.
     */
    private static Connection connection = null;
    private static final String URL = "jdbc:mysql://localhost:3306/vaultxbank";
    private static final String USER = "root";
    private static final String PASS = "Sri2004";

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Tells Java to use the MySQL Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("VaultX: Connection Secured.");
            } catch (ClassNotFoundException e) {
                System.err.println("VaultX: MySQL Driver missing!");
            }
        }
        return connection;
    }
}
