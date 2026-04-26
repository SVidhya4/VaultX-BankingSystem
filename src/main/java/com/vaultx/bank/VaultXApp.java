package com.vaultx.bank;

import com.vaultx.bank.dao.*;
import com.vaultx.bank.factory.UserFactory;
import com.vaultx.bank.service.*;
import com.vaultx.bank.ui.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.*;

public class VaultXApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace(); }

        UIManager.put("Button.background", new Color(240, 240, 240));
        UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("TextField.margin", new Insets(5, 5, 5, 5));

        UserFactory userFactory = new UserFactory();
        UserDAO userDAO = new UserDAO(userFactory);
        AccountDAO accountDAO = new AccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        AuthService authService = new AuthService(userDAO);
        BankService bankService = new BankService(accountDAO, transactionDAO);

        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame(authService, bankService, accountDAO, transactionDAO);
            login.setLocationRelativeTo(null); // Center on screen
            login.setVisible(true);
        });
    }
}