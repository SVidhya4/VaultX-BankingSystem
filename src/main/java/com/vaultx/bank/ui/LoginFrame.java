package com.vaultx.bank.ui;

import com.vaultx.bank.dao.AccountDAO;
import com.vaultx.bank.dao.TransactionDAO;
import com.vaultx.bank.service.AuthService;
import com.vaultx.bank.service.BankService;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private AuthService authService;
    private BankService bankService;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    private JTextField txtUsername = new JTextField(15);
    private JPasswordField txtPassword = new JPasswordField(15);
    private JButton btnLogin = new JButton("Login");

    public LoginFrame(AuthService authService, BankService bankService, AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.authService = authService;
        this.bankService = bankService;
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;

        setTitle("VaultX - Secure Login");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(242, 244, 246));

        JPanel loginCard = new JPanel(new GridBagLayout());
        loginCard.setBackground(Color.WHITE);
        loginCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username row
        gbc.gridx = 0; gbc.gridy = 0;
        loginCard.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        loginCard.add(txtUsername, gbc);

        // Password row
        gbc.gridx = 0; gbc.gridy = 1;
        loginCard.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        loginCard.add(txtPassword, gbc);

        // Login Button row
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2; // Span across both columns
        gbc.insets = new Insets(20, 10, 0, 10);
        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        loginCard.add(btnLogin, gbc);

        this.add(loginCard);

        btnLogin.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String user = txtUsername.getText();
        String pass = new String(txtPassword.getPassword());

        if (authService.login(user, pass)) {
            new DashboardFrame(authService, bankService, accountDAO, transactionDAO).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}